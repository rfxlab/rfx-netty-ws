package rfx.core.netty;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import rfx.core.netty.ws.HttpNodeResourceHandler;
import rfx.core.netty.ws.HttpNodeRestHandler;

@ApplicationPath("/")
public class HttpNodeApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        // register root resource
        classes.add(HttpNodeRestHandler.class);
        classes.add(HttpNodeResourceHandler.class);
        return classes;
    }
}
