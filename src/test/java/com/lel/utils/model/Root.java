package com.lel.utils.model;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class Root<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 919687882098178371L;
	
	//@XStreamOmitField
	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	
}
