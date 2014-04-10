package common.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import common.CoreException;
import common.jersey.processor.JsonTemplateProcessor;
import common.jersey.processor.ThymeleafTemplateProcessor;

import contoller.Templates;


@ApplicationPath("app")
public class MyApplication extends ResourceConfig {
	public MyApplication() {
		packages(Templates.class.getPackage().getName());

//		register(org.glassfish.jersey.server.mvc.MvcFeature.class);

		register(ThymeleafTemplateProcessor.class);
		register(JsonTemplateProcessor.class);
		register(CoreException.class);
		register(new MyBinder());
	}
}