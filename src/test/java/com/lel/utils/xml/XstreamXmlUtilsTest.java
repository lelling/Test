package com.lel.utils.xml;

import org.testng.annotations.Test;

import com.lel.utils.model.Root;
import com.lel.utils.model.User;

public class XstreamXmlUtilsTest {
	
	@Test
	public void test(){
		Root<User> root = new Root<>();
		User user = new User();
		root.setT(user);
		System.out.println(XstreamXmlUtils.toXml(root));
	}
}
