package com.xcm.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用 - 消息
 */
public class Message implements Serializable {

	/**
	 * 类型
	 */
	public enum Type {

		success("成功"),
		error("错误");

		Type(String desc) {
			this.desc = desc;
		}

		private String desc;

		public String getDesc() {
			return desc;
		}
	}

	private Type type;//类型
	private String code;//状态码(0:成功,1:失败)
	private String content;//描述
	private Object data;//数据

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 初始化对象
	 */
	public Message() {
		super();
	}

	/**
	 * 初始化对象
	 *
	 * @param type 类型
	 * @param data 数据
	 */
	public Message(Type type, Object data) {
		super();
		this.type = type;
		this.code = String.valueOf(type.ordinal());
		this.content = type.getDesc();
		this.data = data;
	}

	/**
	 * 初始化对象
	 *
	 * @param type    类型
	 * @param content 内容
	 * @param data    数据
	 */
	public Message(Type type, String content, Object data) {
		super();
		this.type = type;
		this.code = String.valueOf(type.ordinal());
		this.content = content;
		this.data = data;
	}

	/**
	 * 返回成功消息
	 *
	 * @return
	 */
	public static Message success() {
		return new Message(Type.success, null);
	}

	/**
	 * 返回成功消息
	 *
	 * @param content 描述
	 * @return
	 */
	public static Message success(String content) {
		return new Message(Type.success, content, null);
	}

	/**
	 * 返回成功消息
	 *
	 * @param content 描述
	 * @param data    数据
	 * @return
	 */
	public static Message success(String content, Object data) {
		return new Message(Type.success, content, data);
	}

	/**
	 * 返回成功消息
	 *
	 * @param content 描述
	 * @param key     数据key
	 * @param value   数据value
	 * @return
	 */
	public static Message success(String content, String key, Object value) {
		Map<String, Object> data = new HashMap<>();
		data.put(key, value);
		return new Message(Type.success, content, data);
	}

	/**
	 * 返回错误消息
	 *
	 * @return
	 */
	public static Message error() {
		return new Message(Type.error, null);
	}

	/**
	 * 返回其他错误消息
	 *
	 * @param content 描述
	 * @param data    数据
	 * @return
	 */
	public static Message error(String content, Object data) {
		return new Message(Type.error, content, data);
	}

	/**
	 * 返回其他错误消息
	 *
	 * @param content 描述
	 * @return
	 */
	public static Message error(String content) {
		return new Message(Type.error, content, null);
	}

	@Override
	public String toString() {
		return "ApiMessage [type=" + type + ", code=" + code + ", content=" + content + ", data=" + data + "]";
	}
}