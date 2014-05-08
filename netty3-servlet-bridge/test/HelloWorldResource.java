/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

//The Java class will be hosted at the URI path "/helloworld"
@Path("/test")
public class HelloWorldResource {

    // The Java method will process HTTP GET requests
    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }

    @GET
    @Path("/track")
    @Produces(MediaType.APPLICATION_JSON)
    public Track getTrack() {
        Track track = new Track("Enter Sandman","Metallica");
        

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
