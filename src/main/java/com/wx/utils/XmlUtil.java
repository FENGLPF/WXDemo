package com.wx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
	private static Logger logger = Logger.getLogger(WXUtil.class);
	/**
	 * 读取xml并解析成map
	 * @param filePath
	 * @return
	 */
	public static Map<String, String> getXml(String filePath){
		InputStream ins = null;
 		try {
 			File file = new File(filePath);
			ins = new FileInputStream(file);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 		return xmlToMap(ins);
		
	}
	
	/**
	 * 保存xml
	 * @param filePath
	 * @param xmlStr
	 * @return
	 */
	public static int setXml(String filePath,String xmlStr){
		int i = 0;
		try {
			File file = new File(filePath);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			XMLWriter xmlWriter = new XMLWriter(fileOutputStream,OutputFormat.createPrettyPrint());
			xmlWriter.setEscapeText(false);
			xmlWriter.write(xmlStr);
			xmlWriter.close();
			i = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return i;
	}
	
	/**
	 * xml解析成map
	 * @param ins
	 * @return
	 */
	public static Map<String, String> xmlToMap(InputStream ins){
		Map<String, String> map = new HashMap<String, String>();
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(ins);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e);
		}
		Element root = document.getRootElement();
        List<Element> elementsList = root.elements();
        for (Element element : elementsList) {
			map.put(element.getName(), element.getText());
		}
        return map;
	}
	
}
