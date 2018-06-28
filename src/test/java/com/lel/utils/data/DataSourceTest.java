package com.lel.utils.data;

import org.testng.annotations.Test;

import com.lel.base.data.DataSource;
import com.lel.base.data.JsonDataSource;
import com.lel.utils.json.GsonUtils;
import com.lel.utils.model.User;
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
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXml")
	public void test(){
		System.out.println(XstreamXmlUtils.toXml("测试无参"));
	}
	
	/**
	 * 【示例】基本类型（int）<br>
	 * @param str
	 */
	@Test(dataProviderClass = DataSource.class, dataProvider = "dataProviderMXml")
	public void testInt(int i){
		System.out.println("测试int:" + i);
	}
	
	/**
	 * 【示例】对象<br>
	 * @param str
	 */
	@Test(dataProviderClass = JsonDataSource.class, dataProvider = "dataProviderJson")
	public void testUserMore(User user, User u2, Double i){
		System.out.println("测试user:" + GsonUtils.toJson(user) + "-" + GsonUtils.toJson(u2) + "-" + i);
	}
	
	/**
	 * 【示例】对象<br>
	 * @param str
	 */
	@Test(dataProviderClass = JsonDataSource.class, dataProvider = "dataProviderJson")
	public void testUser(User user){
		System.out.println("测试user:" + GsonUtils.toJson(user));
	}
	
}
