package common;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ParamException;

@Provider
public class CoreException implements ExceptionMapper<ParamException> {
    @Override
    public Response toResponse(ParamException exception) {
    	System.out.println("aaa");
        return Response.status(Status.BAD_REQUEST).entity(exception.getParameterName() + " incorrect type").build();
    }
}