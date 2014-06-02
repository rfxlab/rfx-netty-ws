package rfx.test.akka.actor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.google.common.base.Stopwatch;

public class TestActor {
	
	static AtomicInteger counter = new AtomicInteger(0);
	static int MAX_POOL_SIZE = 20;
	private static final int MIN = 0;
	private static final int MAX = MAX_POOL_SIZE - 1;	
	
	static Map<String,ActorRef> greetingActors = new HashMap<>(MAX_POOL_SIZE);
	static Map<String,ActorRef> workingActors = new HashMap<>(MAX_POOL_SIZE);
	static Map<String,ActorRef> byebyeActors = new HashMap<>(MAX_POOL_SIZE);

	public static class Person implements Serializable {
		private static final long serialVersionUID = -3510557864155279314L;
		public final String who;
		public Person(String who) {
			this.who = who;
		}
	}
	
	static final int randomActorId(){			
		int partition = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
		return partition;
	}
	
	public static class ByeByeActor extends UntypedActor {
		LoggingAdapter log = Logging.getLogger(getContext().system(), this);
		public void onReceive(Object message) throws Exception {
			if (message instanceof Person){
				log.info("Byebye " + ((Person) message).who);
				counter.incrementAndGet();
			}
		}
	}
	
	public static class WorkingActor extends UntypedActor {
		LoggingAdapter log = Logging.getLogger(getContext().system(), this);
		public void onReceive(Object message) throws Exception {
			if (message instanceof Person){
				log.info("Working with " + ((Person) message).who);
				byebyeActors.get("ByeByeActor"+randomActorId()).tell(message, self());
			}
		}
	}

	public static class GreetingActor extends UntypedActor {
		LoggingAdapter log = Logging.getLogger(getContext().system(), this);
		public void onReceive(Object message) throws Exception {
			if (message instanceof Person){
				log.info("Hello " + ((Person) message).who);
				workingActors.get("WorkingActor"+randomActorId()).tell(message, self());
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {		
		ActorSystem system = ActorSystem.create("MySystem");		
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		for (int i = 0; i < MAX_POOL_SIZE; i++) {
			String name = "GreetingActor"+i;
			ActorRef actor = system.actorOf(Props.create(GreetingActor.class), name );
			greetingActors.put(name, actor);
		}
		
		for (int i = 0; i < MAX_POOL_SIZE; i++) {
			String name = "WorkingActor"+i;
			ActorRef actor = system.actorOf(Props.create(WorkingActor.class), name);
			workingActors.put(name, actor);
		}
		
		for (int i = 0; i < MAX_POOL_SIZE; i++) {
			String name = "ByeByeActor"+i;
			ActorRef actor = system.actorOf(Props.create(ByeByeActor.class), name);
			byebyeActors.put(name, actor);
		}
		
		for (int i = 0; i < MAX_POOL_SIZE; i++) {
			ActorRef actor = greetingActors.get("GreetingActor"+i);
			actor.tell(new Person("person-"+i), ActorRef.noSender());	
			//break;
		}
		
		while(true){
			if(counter.intValue() >= MAX_POOL_SIZE){
				break;
			}
		}
		
		stopwatch.stop();				
		long milis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		
		int avg = (int) Math.floor(MAX_POOL_SIZE / milis);
		String rs = "TestActor processed "+counter.intValue()+" messages, done in (milisecs):"+ milis + ", average 1 milisecs could process "+ avg;
		
		//new Jedis("127.0.0.1",6379).set("TestActor-SIZE-"+MAX_POOL_SIZE, rs);
		System.out.println(rs);
		
		system.shutdown();		
	}
	
	
}