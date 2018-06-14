package com.lel.utils.validator.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.lel.utils.validator.anno.EnumValue;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2778957211159468197L;
	
	/**
	 * 允许为空;非空时，此对象的所有字段需要校验
	 */
	@Valid
	@NotNull(message = "丢失父级用户信息")
	private User pUser;
	
	/**
	 * @NotEmpty 判断是否为null；
	 * @Size 非空时，判断list大小；空时，无效
	 */
	// @NotEmpty(message = "丢失子级用户信息")
	@Size(min = 2, message = "至少包含2个子用户")
	private List<User> children;
	/**
	 * 非空
	 * @Length 非空时的长度限制
	 */
	@NotBlank(message = "姓名非空")
	@Length(min = 6, max = 20, message = "姓名长度范围为[6,20]")
	private String name;
	
	/**
	 * 允许为空，非空时必须为6-20位数字<br>
	 */
	@Pattern(message="密码必须为6-20位数字", regexp="^\\d{6,20}$")
	private String password;

	/**
	 * 允许为空；非空时必须在[18,70]之间
	 */
	@DecimalMin(message="年龄必须大于18", value = "18")
	@DecimalMax(message="年龄必须小于70", value = "70")
	private Integer iage;
	
	/**
	 * 此处不允许为空，空时按0处理；
	 * 非空时，处理同@DecimalMin
	 */
	@Min(message="年龄必须大于18", value = 18)
	@Max(message="年龄必须小于70", value = 70)
	private int age;
	
	/**
	 * 允许为空；非空时校验格式
	 */
	@Email(message = "邮箱格式错误")
	private String mail;
	
	/**
	 * 
	 */
	@Range(message = "工作年限越界", min = 2, max = 10)
	private Integer workYears;
	
	@EnumValue(enumClass = StatusEnum.class, enumMethod = "isValidKey", message = "无效状态")
	@NotEmpty(message = "状态为空")
	private String status;
	
	public Integer getWorkYears() {
		return workYears;
	}

	public void setWorkYears(Integer workYears) {
		this.workYears = workYears;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<User> getChildren() {
		return children;
	}

	public void setChildren(List<User> children) {
		this.children = children;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getIage() {
		return iage;
	}

	public void setIage(Integer iage) {
		this.iage = iage;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getpUser() {
		return pUser;
	}

	public void setpUser(User pUser) {
		this.pUser = pUser;
	}
	
	public enum StatusEnum{
		NORMAL("1","正常"),
		DISABLED("0", "失效");
		private String key;
		private String value;
		private StatusEnum(String key, String value){
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public static boolean isValidKey(String key){
			for (StatusEnum em : StatusEnum.values()) {
                if (em.getKey().equals(key)) {
                    return true;
                }
            }
            return false;
		}
		
		public static boolean isValidValue(String value){
			for (StatusEnum em : StatusEnum.values()) {
                if (em.getValue().equals(value)) {
                    return true;
                }
            }
            return false;
		}
	}
	
}
