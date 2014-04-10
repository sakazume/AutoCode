package model.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import model.AbstractModel;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jsoup.nodes.Document;

import entity.User;

public class ProjectModel extends AbstractModel {
	
	
	XmlModel xmlModel;
	
	
	/**
	 * POM設定JSONファイル読込
	 * @throws IOException
	 */
	private List<PomJsonMap> getPomJson(String key) throws IOException {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		File test = new File("");
		System.out.println(test.getAbsolutePath());
		File file = new File( BASE_DIR + "src/main/resources/json/pom/pom.json");

		HashMap<String,  List<PomJsonMap>> o = 
				mapper.readValue(
						file,
						new TypeReference<HashMap<String , List<PomJsonMap>>>(){}
				);
		
		return o.get(key);
	}
	
	public PomJsonMap getPomJson(String key , String pomKey) throws IOException {
		List<PomJsonMap> list = this.getPomJson(key);
		
		PomJsonMap ret = null;
		
		for(PomJsonMap pomJsonMap:list) {
			if(pomJsonMap.getKey().equals(pomKey)) {
				ret = pomJsonMap;
				break;
			}
		}
		return ret;
	}
	
	
	public PomJsonMap getTemplatePomData(String key) throws IOException {
		return getPomJson("template-engine", key);
	}
	
	public PomJsonMap getOrmPomData(String key) throws IOException {
		return getPomJson("orm", key);
	}
	
	
	public void pomUpdate(User user , List<PomJsonMap> pomList) throws IOException {
		Path src = new File(user.getBaseUrl() , "pom.xml").toPath();
		xmlModel.setDoc(src);
		Document doc = xmlModel.getDoc();
		System.out.println(doc.toString());
	}
	
	
	
	
	
	
	@Data
	public static class PomJsonMap {
		String key;
		PomJson pomjson;
		
		/**
		 * Maven形式のPOMデータを返す
		 * @return
		 */
		public String toPomData() {
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("<dependency>" + "\n");
			builder.append("<groupId>" + pomjson.getGroupId() + "</groupId>" + "\n");
			builder.append("<artifactId>" + pomjson.getArtifactId() +"</artifactId>" + "\n");
			builder.append("<version>" + pomjson.getVersion() +"</version>" + "\n");
			builder.append("</dependency>");
			return builder.toString();
		}
	}
	@Data
	public static class PomJson {
		String groupId;
		String artifactId;
		String version;
	}
}
