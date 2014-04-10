package contoller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Viewable;

@Path("/html")
public class Templates {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Viewable sayHello2() {
		return new Viewable("Login", new SampleModel("Good morning","my friends"));
	}

	public static class SampleModel {
		public String greeting;
		public String name;

		public SampleModel(String greeting, String name) {
			this.greeting = greeting;
			this.name = name;
		}
	}


}
