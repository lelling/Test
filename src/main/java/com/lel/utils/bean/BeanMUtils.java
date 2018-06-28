package com.lel.utils.bean;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BeanMUtils {
	
	public static <T> T mapToBean(Map<String, ?> map, Class<T> clazz){
		if (map == null) {
			return null;
		}
		T t = null;
		try {
			t = clazz.newInstance();
			BeanUtils.populate(t, map);
		} catch (Exception e) {
			throw new RuntimeException("map转bean异常", e);
		}
		return t;
	}
}
