package com.lel.utils.data;

import org.testng.annotations.Test;

import com.lel.base.data.DataSource;
import com.lel.utils.json.GsonUtils;
import com.lel.utils.xml.XstreamXmlUtils;

/**
 * 引用datasource数据源示例
 * @author lel
 *
 */
public class DataSourceTest {

	/**
	 * 【示例】无参-不需要配置xml文件<br>
	 * @param str
	 */
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXmlOne")
	public void test(){
		System.out.println(XstreamXmlUtils.toXml("测试无参"));
	}
	
	/**
	 * 【示例】基本类型（int）<br>
	 * @param str
	 */
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXmlOne")
	public void testInt(int i){
		System.out.println("测试int:" + i);
	}
	/**
	 * 【示例】基本类型数组（int[]）<br>
	 * @param str
	 */
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXmlOne")
	public void testIntArr(int[] iarr){
		System.out.println("测试intArr:" + GsonUtils.toJson(iarr));
	}
	
	/**
	 * 【示例】对象<br>
	 * @param str
	 */
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXmlOne")
	public void testInteger(Integer i){
		System.out.println("测试Integer:" + i);
	}
	
	/**
	 * 【示例】对象数组<br>
	 * @param str
	 */
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXmlOne")
	public void testIntegerArr(Integer[] iarr){
		System.out.println("测试intArr:" + GsonUtils.toJson(iarr));
	}
	
	
	/**
	 * 【示例】多个参数<br>
	 * @param str
	 */
	// TODO
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXmlOne")
	public void testMoreParamsInt(int i, String name){
		System.out.println("测试int:" + i + " - name:" + name);
	}
	
	/**
	 * 【示例】基本类型-多组<br>
	 * @param str
	 */
	// TODO
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXmlMutil")
	public void testMutilParamsInt(){
		System.out.println("无参");
	}
}
