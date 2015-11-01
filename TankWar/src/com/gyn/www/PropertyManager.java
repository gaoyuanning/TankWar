package com.gyn.www;
import java.io.IOException;
import java.util.Properties;

/**
 * �����ļ�������
 * @author ��Զ��
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
	
	private PropertyManager() {}	//��ֹ����new�������
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
}
