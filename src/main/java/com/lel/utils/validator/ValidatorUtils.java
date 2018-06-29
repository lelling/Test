package com.lel.utils.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.HibernateValidator;

import com.lel.utils.model.Result;

/**
 * 参数校验类<br>
 * 可指定分组；若目标类未被多次校验使用，建议不指定分组（目标类字段注解中不要使用groups）<br>
 * @author lel
 *
 */
public class ValidatorUtils {
	
	/**
	 * 快速失败
	 */
	private static Validator VALIDATOR_F = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();
	
	/**
	 * 校验所有字段
	 */
	private static Validator VALIDATOR_NF = Validation.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();
	
	/**
	 * 参数校验
	 * @param obj 需要校验的自定义类
	 * @param failFast 是否快速失败（校验不通过立即返回）
	 * @return
	 */
	private static <T> Result<Set<ConstraintViolation<T>>> validate(T obj, boolean failFast,  Class<?>... groups) {
		Result<Set<ConstraintViolation<T>>> result = new Result<Set<ConstraintViolation<T>>>();
		if (obj.getClass().isPrimitive()) {
			// 基本类型
			result.fail("基本类型不予检查");
			return result;
		}
		Validator validator = null;
		Set<ConstraintViolation<T>> validates = null;
		if (failFast) {
			validator = VALIDATOR_F;
		}else{
			validator = VALIDATOR_NF;
		}
		if (isEmptyArr(groups)) {
			validates = validator.validate(obj);
		}else{
			validates = validator.validate(obj, groups);
		}
        if (CollectionUtils.isEmpty(validates)) {
        	result.success();
        }else{
        	// 校验不通过，第一个错误信息放在msg中，完整错误信息放入data
        	result.fail(validates.iterator().next().getMessage());
        	result.setData(validates);
        }
		return result;
    }
	/**
	 * 参数校验（未指定分组）-快速失败
	 * @param obj 需要校验的自定义类
	 * @return
	 */
	public static <T> Result<Set<ConstraintViolation<T>>> validate(T obj) {
		return validate(obj, true);
    }
	/**
	 * 参数校验(指定分组)-快速失败
	 * @param obj 需要校验的自定义类
	 * @return
	 */
	public static <T> Result<Set<ConstraintViolation<T>>> validate(T obj, Class<?>... groups) {
		return validate(obj, true, groups);
    }
	
	/**
	 * 参数校验(指定分组)
	 * @param obj 需要校验的自定义类
	 * @return
	 */
	public static <T> Result<List<String>> validateLM(T obj, boolean fastFail, Class<?>... groups) {
		Result<Set<ConstraintViolation<T>>> validate = validate(obj, fastFail, groups);
		return coverResult(validate);
    }
	/**
	 * 参数校验(指定分组) - 快速失败
	 * @param obj
	 * @param fastFail
	 * @param groups
	 * @return
	 */
	public static <T> Result<List<String>> validateLM(T obj, Class<?>... groups) {
		return validateLM(obj, true, groups);
	}
	
	/**
	 * 校验结果转换<br>
	 * @param validate
	 * @return
	 */
	private static <T> Result<List<String>> coverResult(Result<Set<ConstraintViolation<T>>> validate){
		Result<List<String>> result = new Result<List<String>>();
		List<String> list = new ArrayList<String>();
		if (validate == null) {
			result.fail("校验结果为空");
			return result;
		}
		if (validate.exeSuccess()) {
			result.success();
		}else{
			Iterator<ConstraintViolation<T>> iterator = validate.getData().iterator();
			while (iterator.hasNext()) {
				list.add(iterator.next().getMessage());
			}
			result.fail(list.get(0));
			result.setData(list);
		}
		return result;
	}
	
	/**
	 * 判断数组是否为空
	 * @param objs
	 */
	public static boolean isEmptyArr(Object... objs){
		if (null == objs || objs.length == 0) {
			return true;
		}
		return false;
	}
	
}
