package com.zftlive.android.model;

import java.io.Serializable;

/**
 * 消息实体Bean
 * @author 曾繁添
 * @version 1.0
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 7491152915368949244L;
	
	/**
	 * 消息ID
	 */
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
