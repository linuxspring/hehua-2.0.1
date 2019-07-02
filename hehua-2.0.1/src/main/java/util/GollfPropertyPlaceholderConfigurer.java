package util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * @author pengguojin
 * @desc springmvc动态读取配置文件
 * @createTime 2017-09-01
 * @project itsm工程
 *
 */
public class GollfPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	public void setGollfPropFiles(Set<String> gollfPropFiles) {
		Properties properties = new Properties();
		try {
			new PropertiesUtil();
			/** 根据dbconfig.properties动态读取地市局配置文件 **/
			Properties prop = PropertiesUtil.getProperties();
			if (prop != null) {
				properties.putAll(prop);
			}
		} catch (Exception e) {
		}
		/** 加载其他通用的配置文件 **/
		Properties generalConfig = new Properties();
		try {
			generalConfig.load(new InputStreamReader(
					PropertiesUtil.class.getClassLoader().getResourceAsStream("/city/" + "druidconfig.properties"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 通过这个方法将自定义加载的properties文件加入spring中
		this.setPropertiesArray(properties, generalConfig);

	}
}
