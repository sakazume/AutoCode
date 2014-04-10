package common.config;
import model.impl.DBModelImpl;
import model.impl.ProjectModel;
import model.impl.XmlModel;
import model.inf.DBModel;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

public class MyBinder extends AbstractBinder {
	@Override
	protected void configure() {
		 // request scope binding
//        bind(LoginServiceImpl.class).to(LoginService.class).in(RequestScoped.class);

		bind(DBModelImpl.class).to(DBModel.class).in(RequestScoped.class);
		bind(XmlModel.class).to(XmlModel.class).in(RequestScoped.class);
		bind(ProjectModel.class).to(ProjectModel.class).in(RequestScoped.class);
		
	}
}
