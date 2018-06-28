package com.lel.base.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.DataProvider;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.lel.base.BaseTest;
import com.lel.utils.validator.ValidatorUtils;
import com.lel.utils.xml.XstreamXmlUtils;

/**
 * 单元测试数据源<br>
 * 数据类型：基本数据类型，自定义对象
 * 单方法数据量：一组测试数据、多组测试数据
 * @TODO 文件提供单个方法还是多个方法的测试数据
 * @author lel
 *
 */
public class DataSource {
	
	private static final String TEST_RESOURCE_PATH = DataSource.class.getResource("/").getPath();
	/**
	 * xml类型数据源路径
	 */
	private static final String SOURCE_XML = "data/xml/";
	
	private static final String FORMAT_FILE_PATH = TEST_RESOURCE_PATH+"%1$s%2$s/%3$s.%4$s";
	
	private static final Object[][] EMPTY_OBJS = new Object[][]{{}};
	
	/***
	 * 根据方法名信息获取xml数据源<br>
	 * xml-data路径为test/resources/data/xml/{className}/{className}.xml
	 * @param method 待运行测试方法
	 * @return
	 */
	@DataProvider
	public static Object[][] dataProviderMXml(Method method){
		Parameter[] parameters = method.getParameters();
		if (ValidatorUtils.isEmptyArr(parameters)) {
			// 无参调用
			return EMPTY_OBJS;
		}
		String methodName = method.getName();
		Class<?> declaringClass = method.getDeclaringClass();	// 当前测试方法归属类
		System.out.println("[DataProvider] for:" + declaringClass.getName() + "." + methodName);
		String dataFile = String.format(FORMAT_FILE_PATH, SOURCE_XML, declaringClass.getSimpleName(), declaringClass.getSimpleName(), "xml");
		System.out.println(dataFile);
		if (!new File(dataFile).exists()) {
			throw new RuntimeException("文件[" + dataFile + "]不存在");
		}
		readNode(dataFile, methodName);
		Object[] params = XstreamXmlUtils.toBeanFromFile(dataFile, Object[].class);
		return new Object[][]{{params}};
	}
	
	private static List<String> readNode(String filePath, String nodeName){
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);
			NodeList nodeList = doc.getElementsByTagName(nodeName);
			int i = 0;
			while (i < nodeList.getLength()) {
				System.out.println(nodeList.item(i++));
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/***
	 * 根据方法名信息获取数据源<br>
	 * @param method 待运行测试方法
	 * @return
	 */
	@DataProvider
	public static Object[][] dataProvider(Method method){
		Class<?> declaringClass = method.getDeclaringClass();	// 当前测试方法归属类
		System.out.println("[DataProvider] for:" + declaringClass.getName() + "." + method.getName());
		
		if (declaringClass == BaseTest.class && "test".equals(method.getName())) {
			return new Object[][]{{"base.test"}};
		}
		// Parameter[] parameters = method.getParameters();
		return null;
	}
	
	/**
	 * 读取文件内容为String
	 * @param filePath
	 * @return
	 */
	private static String readFileToString(String filePath) {
		byte[] strBuffer = null;
		int flen = 0;
		File xmlfile = new File(filePath);
		if (xmlfile.exists() && xmlfile.isFile()) {
			try {
				InputStream in = new FileInputStream(xmlfile);
				flen = (int) xmlfile.length();
				strBuffer = new byte[flen];
				in.read(strBuffer, 0, flen);
			} catch (IOException e) {
				throw new RuntimeException(String.format("读取文件%s异常", filePath), e);
			}
		}
		return new String(strBuffer);
	}
}
