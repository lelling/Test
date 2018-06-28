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
 * 【建议】
 * 校验类为常规类，字段为常规字段，尽量不要继承、组合、泛型属性<br>
 * <br>
 * 【不足】
 * 1-@AssertTrue会使对象不纯粹，且group多时，对于多种场景的参数校验将使得对象变得累赘[尽量少使用]
 * 2-非快速失败模式下，相同的对象每次返回的错误信息（msg）可能不同
 * 3-类（A）中包含list<B>属性且A、B包含相同属性校验时，无法准确指出
 * 4-一个字段在不同的场景下显示的messge不同时，无法添加两个相同的注解（messge不同，指定分组）[此时要使用@AssertTrue]
 * 5-一个字段为数组，且数组元素必须在指定的范围内或特定值[使用正则或@AssertTrue]
 * @author lel
 */
public class ValidatorUtilsTest {
	
	/**
	 * 数据源1-直接new对象<br>
	 * 【注意】非本类调用本类的数据源时，该方法必须为static，否则外部类无法访问； 本类自己调用可为非static<br>
	 */
	@DataProvider(name = "normalUser")
	public static Object[][] createUser(){
		User user = new User();
//		user.setName("lel");
		user.setPassword("1235");
		user.setAge(18);
//		user.setMail("1156677011@.com");
		List<User> children = new ArrayList<>();
		User cu1 = new User();
		cu1.setPhone("2266@qq.com");
		children.add(cu1);
		children.add(new User());
		user.setChildren(children);
		user.setStatus("");
		user.setAge(2);
		return new Object[][] {
		     { user}
	    };
	}
	
	@Test(dataProvider = "normalUser")
	public void validateLM(User user){
		Result<List<String>> validate = ValidatorUtils.validateLM(user, false);
		System.out.println(GsonUtils.toJson(validate));
	}
}
