package com.xcm.util;

import com.xcm.common.Setting;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 工具类 - 路径相关处理
 */
public class PathUtils {

	private static String ROOT_PATH = null;
	private static String CLASS_PATH = null;

	public static void init() {
		try {
			CLASS_PATH = Tools.class.getResource("./").getPath().replace("/com/xcm/util/", "/");
		} catch (Exception e) {
			e.printStackTrace();
			initOtherMethod();
		}
		if (!CLASS_PATH.startsWith("/")) {
			CLASS_PATH = "/" + CLASS_PATH;
		}
		ROOT_PATH = CLASS_PATH.replace("/classes/", "/");
		try {
			CLASS_PATH = URLDecoder.decode(CLASS_PATH, "UTF-8");
			ROOT_PATH = URLDecoder.decode(ROOT_PATH, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 其他获取classes路径方法
	 */
	private static void initOtherMethod() {
		CLASS_PATH = Tools.class.getResource("PathUtils.class").getPath().replace("/com/xcm/util/PathUtils.class", "/");
	}

	/**
	 * 获取classes路径
	 *
	 * @return
	 */
	public static String getClassesPath() {
		return CLASS_PATH;
	}

	/**
	 * 获取classes路径
	 *
	 * @param path 相对于classPath的路径
	 * @return
	 */
	public static String getClassesPath(String path) {
		if (path == null)
			return CLASS_PATH;
		else {
			if (path.startsWith("/")) {
				return getClassesPath(path.substring(1));
			}
			return CLASS_PATH + path;
		}
	}

	/**
	 * 获取项目的根目录路径
	 *
	 * @return
	 */
	public static String getRootPath() {
		return ROOT_PATH;
	}

	/**
	 * 获取项目的根目录路径
	 *
	 * @param path 相对于rootPath的路径
	 * @return
	 */
	public static String getRootPath(String path) {
		if (path == null)
			return ROOT_PATH;
		else {
			if (path.startsWith("/")) {
				return getRootPath(path.substring(1));
			}
			return ROOT_PATH + path;
		}
	}

	/**
	 * 获取网络路径
	 *
	 * @param filePath 文件相对路径
	 * @return
	 */
	public static String getStaticUrl(String filePath) {
		Setting setting = SettingUtils.get();
		if (filePath.startsWith(getRootPath()))
			filePath = filePath.substring(getRootPath().length());
		if (filePath.startsWith(setting.getSiteUrl()))
			return filePath;
		if (filePath.startsWith("/"))
			return setting.getSiteUrl() + filePath;
		return setting.getSiteUrl() + "/" + filePath;
	}
}