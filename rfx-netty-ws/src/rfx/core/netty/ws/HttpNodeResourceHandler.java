package rfx.core.netty.ws;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import rfx.core.util.FileUtils;




@Path("/")
public class HttpNodeResourceHandler {
	@Context
	private UriInfo uriInfo;
	
	String readResourceFile(String filename) {
		try {
			return FileUtils.readFileAsString("./resources" + filename);
		} catch (IOException e) {}
		System.err.println("NOT_FOUND_404 request-filename: "+filename);				
		return "";
	}
	
	@GET	
	@Produces("text/html; charset=utf-8")
	public String index() {		
		return readResourceFile("/html/index.html");
	}
	
	@GET
	@Path("/pages/{path: [-.a-zA-Z0-9_/]+}")
	@Produces("text/html; charset=utf-8")
	public String getPages(@PathParam("path") String path) {
		System.out.println(uriInfo.getPath());
		return readResourceFile("/html/"+uriInfo.getPath());
	}
	
	@GET	
	@Path("/cluster-info")
	@Produces("text/html; charset=utf-8")
	public String clusterInfo() {		
		return readResourceFile("/html/cluster-info.html");
	}
	
	@GET
	@Path("/html/{path: [-.a-zA-Z0-9_/]+}")
	@Produces("text/html; charset=utf-8")
	public String getHtml(@PathParam("path") String path) {
		System.out.println(uriInfo.getPath());
		return readResourceFile("/"+uriInfo.getPath());
	}
	
	@GET
	@Path("/css/{path: [-.a-zA-Z0-9_/]+}")
	@Produces("text/css")
	public String getCss(@PathParam("path") String path) {
		System.out.println(uriInfo.getPath());
		return readResourceFile("/"+uriInfo.getPath());
	}
	
	@GET
	@Path("/js/{path: [-.a-zA-Z0-9_/]+}")
	@Produces("text/javascript")
	public String getJavaScript(@PathParam("path") String path) {
		System.out.println("js path "+path);
		return readResourceFile("/"+uriInfo.getPath());
	}

}
