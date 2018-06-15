package com.lel.utils.validator;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lel.utils.json.GsonUtils;
import com.lel.utils.model.Result;
import com.lel.utils.model.User;

/**
 * hibernate-validator类属性校验<br>
 * 【场景】
 * 1-常规校验(单属性校验：非空，长度（范围），取值范围，正则表达)【ok】<br>
 * 2-组合校验(依赖校验：某个字段值为特定值时，校验指定字段; 关联校验：字段A和字段B中必须有一个非空)【ok-@AssertTrue】<br>
 * 3-枚举值校验【ok】<br>
 * 其他-字段允许为空，但非空时必须符合指定规则【ok】<br>
 * 【不足】
 * 1-快速失败模式下，相同的对象每次返回的错误信息可能不同（结果中的msg）
 * @author lel
 */
public class ValidatorUtilsTest {
	
	/**
	 * 数据源1-直接new对象<br>
	 */
	@DataProvider(name = "normalUser")
	public Object[][] createUser(){
		User user = new User();
//		user.setName("lel");
		user.setPassword("1235");
		user.setAge(18);
//		user.setMail("1156677011@.com");
		List<User> children = new ArrayList<>();
		children.add(new User());
//		children.add(new User());
		user.setChildren(children);
		user.setStatus("2");
		return new Object[][] {
		      new Object[] { user}
	    };
	}
	
	@Test(dataProvider = "normalUser")
	public void validateLM(User user){
		Result<List<String>> validate = ValidatorUtils.validateLM(user, false);
		System.out.println(GsonUtils.toJson(validate));
	}
}
