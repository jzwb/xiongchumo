package com.xcm.common;

import java.util.Date;

/**
 * 通用 - 文件信息
 */
public class FileInfo {

	/**
	 * 文件类型
	 */
	public enum FileType {
		image("图片");

		FileType(String desc) {
			this.desc = desc;
		}

		private String desc;

		public String getDesc() {
			return desc;
		}
	}

	/**
	 * 排序类型
	 */
	public enum OrderType {
		name("名称"),
		size("大小"),
		type("类型");

		OrderType(String desc) {
			this.desc = desc;
		}

		private String desc;

		public String getDesc() {
			return desc;
		}
	}

	private String name;//名称
	private String url;//URL
	private Boolean isDirectory;//是否为目录
	private Long size;//大小
	private Date lastModified;//最后修改日期

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsDirectory() {
		return isDirectory;
	}

	public void setIsDirectory(Boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}