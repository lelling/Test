package com.lel.utils.model;

/**
 * 通用返回结果
 * @author lel
 * @param <T>
 */
public class Result<T> implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1844559448210088748L;
	
	public static final String S_FAIL = "0";
	
	public static final String S_SUCCESS = "1";
	
	public Result(){
		
	}

	public Result(String status,String msg,T data){
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	/**
	 * 1:成功<br>
	 * 0:失败<br>
	 */
	private String status = S_FAIL;
	
	/**
	 * 返回消息
	 */
	private String msg = "系统异常";
	
	/**
	 * 返回结果
	 */
	private T data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}
	
	private void fail(String status, String msg){
		this.status = status;
		this.msg = msg;
	}
	
	public void fail(String msg){
		fail(S_FAIL, msg);
	}
	
	public void success(String msg){
		this.status = S_SUCCESS;
		this.msg = msg;
	}
	public void successT(T t){
		success();
		this.setData(t);
	}
	public void success(){
		success("操作成功");
	}
	public void successT(T t,String msg){
		success(msg);
		this.setData(t);
	}
	
	public boolean exeSuccess(){
		if (S_SUCCESS.equals(this.status)) {
			return true;
		}
		return false;
	}
	
}
