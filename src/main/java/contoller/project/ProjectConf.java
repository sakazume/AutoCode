package contoller.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.impl.ProjectModel;
import model.impl.ProjectModel.PomJsonMap;
import entity.User;

@Path("project")
public class ProjectConf {

	@Context ServletContext context;
	@Context
	HttpServletRequest httpServletRequest;
	@Inject ProjectModel projectModel;
	
	
	@GET
	public String index() throws IOException {
		
		User user = new User();
		user.findById(1L);
		
		// セッションを取得する
		HttpSession session = this.httpServletRequest.getSession();
		// リクエストの時間をセッションに保存する
		session.setAttribute("user", user);

		return "project";
	}
	
	
	@POST
	@Path("config")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED + ";charset=UTF-8", 
        MediaType.APPLICATION_FORM_URLENCODED + "; charset=UTF-8",
        MediaType.APPLICATION_FORM_URLENCODED})
	public void config(
			@FormParam("template-engine") String templateEngine,
			@FormParam("orm") String orm,
			@FormParam("json") String json
			) throws IOException {
		List<PomJsonMap> pomList = new ArrayList<>();
		
		
		pomList.add(projectModel.getTemplatePomData(templateEngine));
		String ormSplit[] = this.getOrmSplit(orm);
		for(String ormStr:ormSplit) {
			PomJsonMap pom = projectModel.getOrmPomData(ormStr);
			pomList.add(pom);
		}
		
		User user = new User();
		user.findById(1L);
		
		
		projectModel.pomUpdate(user, pomList);
		
	}
	
	@GET
	@Path("test")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED + ";charset=UTF-8", 
        MediaType.APPLICATION_FORM_URLENCODED + "; charset=UTF-8",
        MediaType.APPLICATION_FORM_URLENCODED})
	public String test(
			@QueryParam("test") String test
		) {
		
		System.out.println(test);
		
		return "test";
	}
	
	
	/**
	 * 
	 * @param orm
	 * @return
	 */
	private String[] getOrmSplit(String orm) {
		String ormsplit[] = new String[1];
		if( -1 == orm.indexOf("_") ) {
			ormsplit[0] = orm;
		} else {
			ormsplit = orm.split("_");
		}
		return ormsplit;
	}
	

}
