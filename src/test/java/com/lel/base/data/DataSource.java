package com.lel.base.data;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.testng.annotations.DataProvider;

import com.lel.base.BaseTest;
import com.lel.utils.validator.ValidatorUtils;
import com.lel.utils.xml.XstreamXmlUtils;

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
	 * xml-data路径为test/resources/data/xml/{className}/{methodName}.xml
	 * @param method 待运行测试方法
	 * @return
	 */
	@DataProvider
	public static Object[][] dataProviderMXmlMutil(Method method){
		Parameter[] parameters = method.getParameters();
		if (ValidatorUtils.isEmptyArr(parameters)) {
			// 无参调用
			return EMPTY_OBJS;
		}
		Class<?> declaringClass = method.getDeclaringClass();	// 当前测试方法归属类
		System.out.println("[DataProvider] for:" + declaringClass.getName() + "." + method.getName());
		String dataFile = String.format(FORMAT_FILE_PATH, SOURCE_XML, declaringClass.getSimpleName(), method.getName(), "xml");
		System.out.println(dataFile);
		if (!new File(dataFile).exists()) {
			throw new RuntimeException("文件[" + dataFile + "]不存在");
		}
		//
		if (parameters.length == 1) {
			Class<?> pClazz = parameters[0].getType();
			Object item = XstreamXmlUtils.toBeanFromFile(dataFile, pClazz);
			return new Object[][]{{}};
		}
		Object[] params = XstreamXmlUtils.toBeanFromFile(dataFile, Object[].class);
		return new Object[][]{{params}};
	}
	
	/***
	 * 根据方法名信息获取xml数据源<br>
	 * xml-data路径为test/resources/data/xml/{className}/{methodName}.xml
	 * @param method 待运行测试方法
	 * @return
	 */
	@DataProvider
	public static Object[][] dataProviderMXmlOne(Method method){
		Parameter[] parameters = method.getParameters();
		if (ValidatorUtils.isEmptyArr(parameters)) {
			// 无参调用
			return EMPTY_OBJS;
		}
		Class<?> declaringClass = method.getDeclaringClass();	// 当前测试方法归属类
		System.out.println("[DataProvider] for:" + declaringClass.getName() + "." + method.getName());
		String dataFile = String.format(FORMAT_FILE_PATH, SOURCE_XML, declaringClass.getSimpleName(), method.getName(), "xml");
		System.out.println(dataFile);
		if (!new File(dataFile).exists()) {
			throw new RuntimeException("文件[" + dataFile + "]不存在");
		}
		//
		if (parameters.length == 1) {
			Class<?> pClazz = parameters[0].getType();
			Object item = XstreamXmlUtils.toBeanFromFile(dataFile, pClazz);
			return new Object[][]{{item}};
		}
		Object[] params = XstreamXmlUtils.toBeanFromFile(dataFile, Object[].class);
		return new Object[][]{{params}};
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
}
