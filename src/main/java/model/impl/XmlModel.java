package model.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import lombok.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

@Data
public class XmlModel {

	Document doc;

	public void setDoc(String path) {
		java.nio.file.Path src = new File(path).toPath();
		this.setDoc(src);

	}
	
	public void setDoc(java.nio.file.Path path) {
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String xmlStr = new String(bytes , StandardCharsets.UTF_8);
		this.doc = Jsoup.parse(xmlStr.toString(), "", Parser.xmlParser());
	}
	
	
	/**
	 * XMLドキュメントからNameで値を検索
	 * @param xmlDoc
	 * @param value
	 * @return
	 */
	public Element getElmentByName(String value) {
		Elements el = this.doc.getElementsByAttributeValue("name", value);
		return el.get(0);
	}
	
	public void setValueByName(String name,String value) {
		getElmentByName(name).attr("value" , value);
	}
}
