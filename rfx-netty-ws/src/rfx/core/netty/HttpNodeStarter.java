package rfx.core.netty;

public class HttpNodeStarter {	
	public static void main(String[] args) {		
		String host = "*";//all network interfaces
		int port = 16000;
		if(args.length == 2){
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		new HttpNode(host, port).run();
	}
}