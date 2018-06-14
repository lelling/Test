package com.lel.utils.json;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * json转换<br>
 * @author lel
 *
 */
public class GsonUtils {
	
	private static final Gson gson = new Gson();
	
	/**
	 * 忽略部分字段
	 * @param filedNames
	 * @return
	 */
	public static Gson getIgnoreGsonByFiledNames(final String... filedNames ){
		Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {  
            @Override  
            public boolean shouldSkipField(FieldAttributes f) {
            	if (null == filedNames) {
					return false;
				}
                 //过滤掉字段名包含"age"  
            	for(String fileName : filedNames){
            		if (f.getName().equals(fileName)) {
						return true;
					}
            	}
                return false;  
            }  
            @Override  
            public boolean shouldSkipClass(Class<?> clazz) {
				return false;  
//                //过滤掉 类名包含 Bean的类  
//                return clazz.getName().contains("Bean");  
            }  
        }).create();
		return gson;
	}
	
	/**
	 * 生成json串
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		return gson.toJson(obj);
	}
	
	/**
	 * 只将json中有的字段放入class有的字段中<br>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clazz){
		return gson.fromJson(json, clazz);
	}
	/**
	 * 对泛型的转化<br>
	 * 参考fromJsonList方法（List<T>）
	 * @param json
	 * @param typeOfT  类似于 new TypeToken<Result<WinningMod>>(){}.getType() 或 new TypeToken<List<WinningMod>>(){}.getType()
	 * @return
	 */
	public static <T> T fromJson(String json, Type typeOfT){
		return gson.fromJson(json, typeOfT);
	}
	/**
	 * 获取list
	 * @param json
	 * @param itemClazz  list内泛型
	 * @return
	 */
	public static <T> List<T> fromJsonList(String json, Class<T> itemClazz){
		return gson.fromJson(json, new TypeToken<List<T>>(){}.getType());
	}
	
}
