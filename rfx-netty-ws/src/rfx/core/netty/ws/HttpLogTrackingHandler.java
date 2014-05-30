package rfx.core.netty.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

@Path("/tracking")
public class HttpLogTrackingHandler {
	@Context
	private UriInfo uriInfo;
	private static final AtomicInteger count = new AtomicInteger(0);
	
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public String index() {
		System.out.println("uriInfo: " + uriInfo.getRequestUri());
		return "";
	}

	@GET
	@Path("pageview")
	@Produces(MediaType.APPLICATION_JSON)
	public String handle(@DefaultValue("") @QueryParam("reading_url") String readingUrl
						, @DefaultValue("") @QueryParam("referrer") String referrer	) {	
//		System.out.println("readingUrl: " + readingUrl);
//		System.out.println("referrer: " + referrer);
		if(!readingUrl.isEmpty() && ! referrer.isEmpty()){
			int c = count.incrementAndGet();
			System.out.println("---");
			
			List<String> list = Arrays.asList(readingUrl, referrer);
			Observable.from(list).subscribe(new Action1<String>() {
				@Override
				public void call(String arg0) {
					System.out.println(arg0);				
				}			
			});
			System.out.println(c);
		}
		String data = "";
		return data;
	}
	
	
}
