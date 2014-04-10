package model;

import javax.ws.rs.FormParam;

import lombok.Data;


public class ConfigParamModel {
	
	/**
	 * DB接続設定
	 * @author styu03
	 *
	 */
	@Data
	public static class DBConfigParam {
		@FormParam("name")
		String name;
		@FormParam("dbType")
		String dbType;
		@FormParam("url")
		String url;
		@FormParam("schema")
		String schema;
		@FormParam("pass")
		String pass;
		@FormParam("user")
		String user;
		@FormParam("charSet")
		String charSet;
	}
}
