package rfx.core.netty;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.reflections.Reflections;

@ApplicationPath("/")
public class HttpNodeApp extends Application {
	final static String BASE_CONTROLLER_PACKAGE = "rfx.core.netty.ws"; 

    @Override
    public Set<Class<?>> getClasses() {
        final HashSet<Class<?>> classes = new HashSet<Class<?>>();
        // register root resource
        Reflections reflections = new Reflections(BASE_CONTROLLER_PACKAGE);
        Set<Class<?>> clazzes =  reflections.getTypesAnnotatedWith(javax.ws.rs.Path.class);
        for (Class<?> clazz : clazzes) {        	
        	if(! classes.contains(clazz) ){
        		classes.add(clazz);
        		System.out.println("...registered controller class: "+ clazz);
        	}        	
		}
        //classes.add(HttpNodeResourceHandler.class);
        return classes;
    }
}
