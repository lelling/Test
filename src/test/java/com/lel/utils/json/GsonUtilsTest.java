package com.lel.utils.json;

import org.testng.annotations.Test;

import com.lel.base.BaseTest;
import com.lel.utils.model.User;

public class GsonUtilsTest {
	
	@Test(dataProviderClass = BaseTest.class, dataProvider = "dataProvider")
	public void toJson(User user){
		String json = GsonUtils.toJson(user);
		System.out.println(json);
	}
}
