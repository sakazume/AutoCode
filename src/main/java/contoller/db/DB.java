package contoller.db;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.ConfigParamModel.DBConfigParam;
import model.inf.DBModel;
import contoller.AbstractContoller;
import entity.User;



@Path("/db")
public class DB extends AbstractContoller {

	@Context
	ServletContext context;
	@Inject
	DBModel dbmodel;



//	public DBConfigParam create() {
//		return  new DBConfigParam()
//		.set("dbType" , "mysql")
//		.set("url","localhost")
//		.set("pass","pass")
//		.set("user","user1")
//		.set("schema","test_db");
//	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String index() throws IOException {
//		DBConfigParam db = this.create();
		User user = new User();
		user = user.findById(1L);
		
		
		
		return "index";
	}

	@POST
	@Path("config/put")
	@Produces(MediaType.APPLICATION_JSON)
	public DBConfigParam configFile(@BeanParam DBConfigParam dbConfig) throws IOException {

		User user = new User();
		user = user.findById(1L);
		dbmodel.updatePersistenceFile(dbConfig,user);
		return dbConfig;
	}
	
}
