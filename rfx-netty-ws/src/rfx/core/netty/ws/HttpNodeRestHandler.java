package rfx.core.netty.ws;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

@Path("/json")
public class HttpNodeRestHandler {
	@Context
	private UriInfo uriInfo;

	@GET
	@Path("handle-master-request")
	@Produces(MediaType.APPLICATION_JSON)
	public String handle(@DefaultValue("nothing") @QueryParam("action") String action) {	
		System.out.println("action: " + action);
		String data = "";
		return data;
	}

	
	
	@POST
	@Path("deploy-topology")
	@Produces(MediaType.APPLICATION_JSON)
	public String deployTopology(
			@DefaultValue("") @FormParam("base64data") String base64data
			,@DefaultValue("") @FormParam("token") String token
			,@DefaultValue("") @FormParam("filename") String filename
			) {
		System.out.println("base64data: "+ base64data);
		System.out.println("token: "+ token);
		System.out.println("filename: "+ filename);
		return new Gson().toJson("OK");
	}

	// //////////////// SAMPLE CODE ////////////////

	@GET
	@Path("track")
	@Produces(MediaType.APPLICATION_JSON)
	public Track getTrack() {
		Track track = new Track("Enter Sandman", "Metallica");
		System.out.println(System.currentTimeMillis());

		return track;
	}

	@XmlRootElement
	public static class Track {
		String title;
		String singer;

		public Track() {
		}

		public Track(String title, String singer) {
			this.title = title;
			this.singer = singer;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getSinger() {
			return singer;
		}

		public void setSinger(String singer) {
			this.singer = singer;  
		}

		@Override
		public String toString() {
			return "Track [title=" + title + ", singer=" + singer + "]";
		}
	}
	
}
