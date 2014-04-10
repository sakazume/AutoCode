package common.jersey.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonTemplateProcessor implements MessageBodyWriter<Object> {

	@Override
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2,
			MediaType mediaType) {

		if (mediaType.toString().equals(MediaType.APPLICATION_JSON)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public long getSize(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public void writeTo(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(t);

		final PrintStream ps = new PrintStream(entityStream);
		ps.println(json);
		ps.close();

	}
}
