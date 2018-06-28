package com.lel.base.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.log4testng.Logger;

import com.lel.utils.bean.BeanMUtils;
import com.lel.utils.json.GsonUtils;
import com.lel.utils.validator.ValidatorUtils;

public class JsonDataSource {
	
	private static final Logger LOGGER = Logger.getLogger(JsonDataSource.class);
	
	private static final String TEST_RESOURCE_PATH = JsonDataSource.class.getResource("/").getPath();
	
	private static final String SOURCE_XML = "data/json/";
	
	private static final String FORMAT_FILE_PATH = TEST_RESOURCE_PATH+"%1$s%2$s/%3$s.%4$s";
	
	private static final String SUFFIX = "json";
	
	private static final Object[][] EMPTY_OBJS = new Object[][]{{}};
	/**
	 * 校验文件并读取
	 * @param method
	 * @return
	 */
	private static String validateAndReadFile(Method method){
		String methodName = method.getName();
		Class<?> declaringClass = method.getDeclaringClass();	// 当前测试方法归属类
		LOGGER.debug("[DataProvider] for:" + declaringClass.getName() + "." + methodName);
		String dataFile = String.format(FORMAT_FILE_PATH, SOURCE_XML, declaringClass.getSimpleName(), methodName, SUFFIX);
		LOGGER.debug(dataFile);
		if (!new File(dataFile).exists()) {
			throw new RuntimeException("文件[" + dataFile + "]不存在");
		}
		return readFileToString(dataFile);
	}
	
	/**
	 * 校验文件
	 * @param method
	 * @return
	 */
	private static String validate(Method method){
		String methodName = method.getName();
		Class<?> declaringClass = method.getDeclaringClass();	// 当前测试方法归属类
		LOGGER.debug("[DataProvider] for:" + declaringClass.getName() + "." + methodName);
		String dataFile = String.format(FORMAT_FILE_PATH, SOURCE_XML, declaringClass.getSimpleName(), declaringClass.getSimpleName(), SUFFIX);
		LOGGER.debug(dataFile);
		if (!new File(dataFile).exists()) {
			throw new RuntimeException("文件[" + dataFile + "]不存在");
		}
		return dataFile;
	}
	
	/***
	 * 根据方法名信息获取json数据源<br>
	 * json-data路径为test/resources/data/json/{className}/{className}.xml
	 * @param method 待运行测试方法
	 * @return
	 */
	@DataProvider
	public static Object[][] dataProviderJson(Method method){
		Parameter[] parameters = method.getParameters();
		if (ValidatorUtils.isEmptyArr(parameters)) {
			// 无参调用
			return EMPTY_OBJS;
		}
		String data = validateAndReadFile(method);
		if (parameters.length == 1) {
			if (data.startsWith("[")) {
				// 多组测试数据
				Object[] arr = GsonUtils.fromJsonArrByItem(data, parameters[0].getType());
				Object[][] res = new Object[arr.length][];
				int i = 0;
				for(Object obj : arr){
					res[i++] = new Object[]{obj};
				}
				return res;
			}else{
				// 单组测试数据
				Object obj = GsonUtils.fromJson(data, parameters[0].getType());
				return new Object[][]{{obj}};
			}
		}else{
			if (data.startsWith("[")) {
				
			}else{
				Map<String, Object> map = GsonUtils.fromJson(data, Map.class);
				Object[][] res = new Object[1][];
				Object[] obj = new Object[parameters.length];
				int i = 0;
				for(Parameter parameter : parameters){
					String pName = null;
					if (parameter.isNamePresent()) {
						pName = parameters[i].getName();
					}
					Object pb = null;
					if (isPrimitive(parameter.getType())) {
						pb = map.get(pName);
					}else{
						Map<String, Object> p = (Map<String, Object>) map.get(pName);
						pb = BeanMUtils.mapToBean(p, parameters[i].getType());
					} 
					obj[i++] = pb;
				}
				res[0] = obj;
				return res;
			}
		}
		return EMPTY_OBJS;
	}
	
	private static boolean isPrimitive(Class clazz){
		if (Integer.class == clazz
				|| Double.class == clazz
				|| Float.class == clazz
				|| Long.class == clazz
				|| Short.class == clazz
				|| Character.class == clazz
				|| Boolean.class == clazz
				|| Byte.class == clazz) {
			return true;
		}
		return false;
	}
	/***
	 * 根据方法名信息获取json数据源<br>
	 * json-data路径为test/resources/data/json/{className}/{className}.xml
	 * @param method 待运行测试方法
	 * @return
	 */
	@DataProvider
	public static Object[][] dataProviderJsonSignal(Method method){
		Parameter[] parameters = method.getParameters();
		if (ValidatorUtils.isEmptyArr(parameters)) {
			// 无参调用
			return EMPTY_OBJS;
		}
		String dataFile = validate(method);
		Object obj = GsonUtils.fromJsonByFile(dataFile, parameters[0].getType());
		return new Object[][]{{obj}};
	}
	
	/***
	 * 根据方法名信息获取json数据源<br>
	 * json-data路径为test/resources/data/json/{className}/{className}.xml
	 * @param method 待运行测试方法
	 * @return
	 */
	@DataProvider
	public static Object[][] dataProviderJsonMore(Method method){
		Parameter[] parameters = method.getParameters();
		if (ValidatorUtils.isEmptyArr(parameters)) {
			// 无参调用
			return EMPTY_OBJS;
		}
		String dataFile = validate(method);
		Object[] arr = GsonUtils.fromJsonByFileToArr(dataFile, parameters[0].getType());
		Object[][] res = new Object[arr.length][];
		int i = 0;
		for(Object obj : arr){
			res[i++] = new Object[]{obj};
		}
		return res;
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
