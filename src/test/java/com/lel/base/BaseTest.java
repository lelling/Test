package com.lel.base;

import org.testng.annotations.Test;

import com.lel.base.data.DataSource;
import com.lel.utils.model.User;

/**
 * 测试基类
 * @author lel
 * 
 */
public class BaseTest {
	
	/**
	 * 引用xml数据源示例<br>
	 * @param str
	 */
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXml")
	public void testStr(String str){
		
	}
	/**
	 * 引用xml数据源示例<br>
	 * @param str
	 */
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXml")
	public void testUser(User user){
		
	}
	
	/**
	 * 引用数据源示例<br>
	 * @param str
	 */
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProvider")
	public void testStrOut(String str){
		System.out.println(str);
	}
}
