package server;

import java.util.Random;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.glassfish.jersey.servlet.ServletContainer;

import common.config.MyApplication;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		server.setSessionIdManager(new HashSessionIdManager(new Random()));
		
		ServletContextHandler ctx = new ServletContextHandler(server, "/",
				false, false);
		ctx.setContextPath("/");
		ctx.setResourceBase("src/main/webapp");
		
		ResourceCollection resources = new ResourceCollection(new String[] {
				"src/main/webapp", 
			});
		
		
		ctx.setBaseResource(resources);

	    
		MyApplication app = new MyApplication();
		
		ServletContainer sc = new ServletContainer(app);
		ctx.addServlet(new ServletHolder(sc), "/rest/*");
		server.setHandler(ctx);
		
		
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
