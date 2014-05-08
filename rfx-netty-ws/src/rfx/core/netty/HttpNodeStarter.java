package rfx.core.netty;


public class HttpNodeStarter {	
	
	public static void main(String[] args) {
		
		new HttpNode("localhost", 8080).run();
	}
}