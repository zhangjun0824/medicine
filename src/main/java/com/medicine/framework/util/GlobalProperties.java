package com.medicine.framework.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 系统属性值获取共通类
 *
 */
public class GlobalProperties {
	private static Properties _globalProperties = null;
	private static Object lockObject = new Object();

	private static Properties getGlobalProperties() {

		if (_globalProperties == null) {
			synchronized (lockObject) {
				if (_globalProperties == null) {
					try {
						init();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return _globalProperties;
	}

	/**
	 * 初始化属性对象
	 *
	 * @throws Exception
	 *             抛出异常
	 */
	public static synchronized void init() throws Exception {
		_globalProperties = new Properties();
		java.net.URL url = Thread.currentThread().getContextClassLoader().getResource("global.properties");

		if (url == null) {
			url = GlobalProperties.class.getClassLoader().getResource("global.properties");
		}
		java.io.InputStream objInputStream = url.openStream();
		_globalProperties.load(objInputStream);
		objInputStream.close();
    }

	/**
	 * 获取全局属性文件中定义的属性
	 *
	 * @param key
	 *            属性关键字
	 * @return 属性值
	 */
	public static String getGlobalProperty(String key) {
		Properties globalProperties = getGlobalProperties();
		if (globalProperties.containsKey(key))
			return globalProperties.getProperty(key);
		else
			return null;
	}

	// 根据Key读取Value
	public static String GetValueByKey(String filePath, String key) throws IOException {
		Properties pps = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			pps.load(in);
			String value = pps.getProperty(key);
			return value;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            in.close();
        }
    }

	/**
	 * 修改或添加属性
	 *
	 * @param key
	 * @param value
	 */
	public void setGlobalProperty(String key, String value) {
	
			Properties globalProperties = getGlobalProperties();
			globalProperties.setProperty(key, value);
		
	}

	/**
	 * 保存文件
	 *
	 * @throws Exception
	 */
	public void saveGlobalProperty() throws Exception {
		Properties globalProperties = getGlobalProperties();
		FileOutputStream fos = new FileOutputStream("global.properties");
		globalProperties.store(fos, "Copyright (c) SCBIM");

	}

    // 写入Properties信息
    public static void WriteProperties(String filePath, String pKey, String pValue) throws IOException {
        Properties pps = new Properties();
        File file = new File(filePath);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        InputStream in = new FileInputStream(filePath);

        // 从输入流中读取属性列表（键和元素对）
        pps.load(in);
        // 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
        // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        OutputStream out = new FileOutputStream(filePath);
        pps.setProperty(pKey, pValue);
        // 以适合使用 load 方法加载到 Properties 表中的格式，
        // 将此 Properties 表中的属性列表（键和元素对）写入输出流
        pps.store(out, "Update " + pKey + " name");
        out.close();
    }
}
