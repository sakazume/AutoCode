package common.jersey.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.server.mvc.Viewable;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * テンプレートエンジン動作設定
 * @author sakadume
 *
 */
//@Provider
public class ThymeleafTemplateProcessor implements MessageBodyWriter<Viewable> {
	@Context
	ServletContext servletContext;

	TemplateEngine templateEngine;

	@Inject
	private javax.inject.Provider<Ref<HttpServletRequest>> requestProviderRef;
	@Inject
	private javax.inject.Provider<Ref<HttpServletResponse>> responseProviderRef;


	public ThymeleafTemplateProcessor() {
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheTTLMs(3600000L);

		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
	}

	public static TemplateEngine createTemplateEngine() {
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheTTLMs(3600000L);

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		return templateEngine;
	}

	@Override
	public long getSize(Viewable arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
		return 0;
	}

	@Override
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2, MediaType mediaType) {

		if( mediaType.toString().equals(MediaType.TEXT_HTML) ) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void writeTo(Viewable viewable, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4,
			MultivaluedMap<String, Object> arg5, OutputStream out) throws IOException, WebApplicationException {
		out.flush();



		WebContext context = new WebContext(
				requestProviderRef.get().get(),
				responseProviderRef.get().get(),
				servletContext,
				responseProviderRef.get().get().getLocale());

		Map<String, Object> variables = new HashMap<>();



		//画面で使用する変数名
		variables.put("it", viewable.getModel());
		context.setVariables(variables);

		//テンプレート読込
//		TemplateEngine templateEngine = this.createTemplateEngine();
		String str = templateEngine.process(viewable.getTemplateName(), context);

		final PrintStream ps = new PrintStream(out);
		ps.println(str);
		ps.close();

	}
}