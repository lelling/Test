package com.lel.utils.xml;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XstreamXmlUtils {
	
	private static final String UTF_8 = "utf-8";
	
	private static XStream  iXstream;
	
	private static XStream  oXstream;
	
	static{
		iXstream = new XStream(new DomDriver(UTF_8, new XmlFriendlyNameCoder("-_", "_")));
		iXstream.autodetectAnnotations(true);
		oXstream = new XStream(new DomDriver(UTF_8));
		oXstream.autodetectAnnotations(true);
	}
	
	/**
	 * 对象转字符串
	 * @param obj
	 * @return
	 */
	public static String toXml(Object obj){
		if (obj == null) {
			return "";
		}
		iXstream.processAnnotations(obj.getClass());
		return iXstream.toXML(obj);
	}
	
	/**
	 * xml字符串转类对象
	 * @param xml
	 * @param clazz
	 * @return
	 */
	public static <T> T toBeanFromStr(String xml, Class<T> clazz){
		if (StringUtils.isEmpty(xml)) {
			return null;
		}
		oXstream.processAnnotations(clazz);
		return (T) oXstream.fromXML(xml);
	}
	
	/**
	 * 文件直转对象<br>
	 * @param xmlFile
	 * @param clazz
	 * @return
	 */
	public static <T> T toBeanFromFile(String xmlFile, Class<T> clazz){
		if (StringUtils.isEmpty(xmlFile)) {
			return null;
		}
		oXstream.processAnnotations(clazz);
		return (T) oXstream.fromXML(new File(xmlFile));
	}
}
