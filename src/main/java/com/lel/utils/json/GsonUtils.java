package com.lel.utils.json;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * json转换<br>
 * 【使用】
 * 1-创建默认配置选项，直接Gson gson = new Gson();<br>
 * 2-创建自定义配置选项，Gson gson = new GsonBuilder().serializeNulls().create(); <br>
 * 可创建如下类型：
 * Gson gson = new GsonBuilder()
         .excludeFieldsWithoutExposeAnnotation() // 不对没有用@Expose注解的属性进行操作
         .enableComplexMapKeySerialization() // 当Map的key为复杂对象时,需要开启该方法
         .serializeNulls() // 当字段值为空或null时，依然对该字段进行转换
         .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")  //时间转化为特定格式
         .setPrettyPrinting()  //对结果进行格式化，增加换行
         .disableHtmlEscaping()  //防止特殊字符出现乱码
         .registerTypeAdapter(User.class,new UserAdapter())  //为某特定对象设置固定的序列或反序列方式，自定义Adapter需实现JsonSerializer或者JsonDeserializer接口
         .create();
 * Gson的注解： @Expose，包含serialize和deserialize属性是可选的，默认两个都为true
 * 【注意】 直接new Gson时， @Expose 无效，此时可用@SerializedName 指定名称
 * @author lel
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
	
	/**
	 * 从文件直接读取json
	 * @param filePath
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJsonByFile(String filePath, Class<T> clazz) {
		try {
			FileInputStream in = new FileInputStream(filePath);
			Reader reader = new InputStreamReader(in, "UTF-8");
			return gson.fromJson(reader, clazz);
		} catch (JsonSyntaxException | JsonIOException  e) {
			throw new RuntimeException("json解析异常");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("无效文件路径");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("不支持的编码格式");
		}
	}
}
