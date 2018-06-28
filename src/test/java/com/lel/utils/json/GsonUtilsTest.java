package com.lel.utils.json;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.google.gson.reflect.TypeToken;
import com.lel.base.BaseTest;
import com.lel.utils.model.User;

public class GsonUtilsTest {
	
	@Test(dataProviderClass = BaseTest.class, dataProvider = "dataProvider")
	public void toJson(User user){
		String json = GsonUtils.toJson(user);
		System.out.println(json);
	}
	
	@Test
	public void listToJson(){
		List<User> users = new ArrayList<>();
		User user1 = new User();
		user1.setName("lel");
		user1.setPassword("ling");
		users.add(user1);
		User user2 = new User();
		user2.setName("oko");
		user2.setPassword("kkkkkk");
		users.add(user2);
		String json = GsonUtils.toJson(users);
		System.out.println(json);
		
		List<User> fromJson = GsonUtils.fromJson(json, new TypeToken<List<User>>(){}.getType());
		System.out.println(GsonUtils.toJson(fromJson));
		
		List<User> fromJsonList = GsonUtils.fromJsonList(json, User.class);
		for(User u:fromJsonList){
			System.out.println(GsonUtils.toJson(u));
			u.setPhone("13851779635");;
		}
		
		User u3 = new User();
		u3.setName("new add");
		fromJsonList.add(u3);
		System.out.println(GsonUtils.toJson(fromJsonList));
	}
}
