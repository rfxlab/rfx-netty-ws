package rfx.core.netty;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import net.javaforge.netty.servlet.bridge.ServletBridgeChannelPipelineFactory;
import net.javaforge.netty.servlet.bridge.config.ServletConfiguration;
import net.javaforge.netty.servlet.bridge.config.WebappConfiguration;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.sun.jersey.spi.container.servlet.ServletContainer;


/**
 * @author trieu
 * 
 *  the master HTTP node can be received HTTP request from workers, supervisors, web browsers
 *
 */
public class HttpNode {

	String masterHostname;
	int masterHttpPort;
    static final Timer theTimer = new Timer(true);
    static long timeToMonitoringWorkers = 10000;//10 seconds

    public HttpNode(String masterHostname, int masterHttpPort) {
    	this.masterHostname = masterHostname;
        this.masterHttpPort = masterHttpPort;
    }

    public void run() {
    	long start = System.currentTimeMillis();    	
        try {        	
        	loadServletBootstrap(this.masterHttpPort);
		} catch (Exception e) {			
			System.err.println(e.getMessage());
			System.exit(1);
		} finally {
			System.out.println("started OK at host:" + masterHostname + " http-port:" + masterHttpPort);	
		}
        long end = System.currentTimeMillis();
        //LogUtil.i(">>> Server started in "+(end - start)+" ms .... <<< ");
    }   
    
    void loadServerBootstrap(ChannelPipelineFactory factory, int port){
    	ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
		                Executors.newCachedThreadPool(),
		                Executors.newCachedThreadPool()));
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setPipelineFactory(factory);		
		bootstrap.bind(new InetSocketAddress(this.masterHostname, port));
    }
    
    void loadServletBootstrap(int port){
  	  	
        // Configure the server.
        Executor bossExecutor = Executors.newCachedThreadPool();
        Executor workerExecutor = Executors.newCachedThreadPool();
        NioServerSocketChannelFactory factory = new NioServerSocketChannelFactory(bossExecutor, workerExecutor );
        final ServerBootstrap bootstrap = new ServerBootstrap(factory);

        ServletConfiguration servletConfig = new ServletConfiguration(ServletContainer.class, "/*").addInitParameter("javax.ws.rs.Application", HttpNodeApp.class.getName());
        WebappConfiguration webapp = new WebappConfiguration();
        webapp.addServletConfigurations(servletConfig);
        webapp.setDebug(true);

        // Set up the event pipeline factory.
        final ServletBridgeChannelPipelineFactory servletBridge = new ServletBridgeChannelPipelineFactory(webapp);
        bootstrap.setPipelineFactory(servletBridge);

        // Bind and start to accept incoming connections.
        final Channel serverChannel = bootstrap.bind(new InetSocketAddress(this.masterHostname, port));
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                servletBridge.shutdown();
                serverChannel.close().awaitUninterruptibly();
                bootstrap.releaseExternalResources();
            }
        });
    }
    
}
