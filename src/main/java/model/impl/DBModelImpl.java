package model.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import model.AbstractModel;
import model.ConfigParamModel.DBConfigParam;
import model.inf.DBModel;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.nodes.Element;

import entity.Config;
import entity.User;

public class DBModelImpl extends AbstractModel implements DBModel{

	@Inject
	private XmlModel xml;
	@Context
	ServletContext context;

	public DBModelImpl() {
		super();
	}

	@Override
	public void updatePersistenceFile(DBConfigParam dbConfig , User user) throws IOException {
		
		File persistenceFile = new File(user.getBaseUrl() , "src/main/webapp/WEB-INF/classes/META-INF/persistence.xml");
		
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(dbConfig);

		Config config = new Config();
		config = config.findByUser(user);
		config.setValueJson(json);
		config.setConfigType("dbConfig");
		config.save();
		
		
		
		//XMLモデルを初期化
//		String path = context.getRealPath("resources/xml/persistence.xml");
//		String path = persistenceFile.toPath():
		xml.setDoc(persistenceFile.toPath());

		StringBuilder urlBuf = new StringBuilder();

		//JDBCドライバー設定
		switch(dbConfig.getDbType()) {
		case "mysql":
			Element el = xml.getElmentByName("javax.persistence.jdbc.driver");
			el.attr("value" , "com.mysql.jdbc.Driver");
			urlBuf.append("jdbc:mysql://");
			break;
		}

		urlBuf.append(
				dbConfig.getUrl() +
				"/" + dbConfig.getSchema() +
				"?characterEncoding=" + dbConfig.getCharSet()
			);

//		xml.getDoc().getElementsByTag("persistence-unit").get(0).attr("name" , dbConfig.getName());
		xml.setValueByName("javax.persistence.jdbc.url",urlBuf.toString());
		xml.setValueByName("javax.persistence.jdbc.user",dbConfig.getUser());
		xml.setValueByName("javax.persistence.jdbc.password",dbConfig.getPass());
		
		this.debug(xml.toString());
		Charset charset = Charset.forName("UTF-8");
		String s = xml.toString();
		try (BufferedWriter writer = Files.newBufferedWriter(persistenceFile.toPath(), charset)) {
		    writer.write(s, 0, s.length());
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}

	}

	@Override
	public void dbConfigPut(DBConfigParam dbConfig) {


	}

	@Override
	public void dbConfigDelete(DBConfigParam dbConfig) {

	}



}
