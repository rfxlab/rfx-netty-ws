package rfx.core.netty;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.reflections.Reflections;

@ApplicationPath("/")
public class HttpNodeApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        // register root resource
        Reflections reflections = new Reflections("rfx.core.netty.ws");
        Set<Class<?>> clazzes =  reflections.getTypesAnnotatedWith(javax.ws.rs.Path.class);
        for (Class<?> clazz : clazzes) {
        	classes.add(clazz);
        	System.out.println("Register Rest Controller "+ clazz);
		}
        //classes.add(HttpNodeResourceHandler.class);
        return classes;
    }
}
