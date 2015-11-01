package com.gyn.www;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件控制类
 * @author 高远宁
 *
 */
public class PropertyManager {
	private static Properties properties = new Properties();
	
	static {
		try {
			properties.load(PropertyManager.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private PropertyManager() {}	//防止别人new这个对象
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
}
