package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/**
 * 
 * @author pengguojin
 * @desc 读取配置文件
 * @createTime 2017-09-01
 * @project itsm工程
 *
 */
public class PropertiesUtil {
	private static Document document = null;
	// dbconfig.properties配置文件
	private static Properties propConfig = new Properties();
	// 动态读取的配置文件
	private static Properties prop = new Properties();
	// 地市局编码
	private static String configCityCode = null;
	static {
		try {
			// 静态加载，项目启动只调用一次
			// 获取地市局编码
			propConfig.load(new InputStreamReader(
					PropertiesUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties"), "UTF-8"));
			configCityCode = propConfig.getProperty("config.city.code");
			// 根据地市局编码，获取配置文件方法
			loadProps();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 静态加载-动态读取的配置文件信息 add by 2018-01-14
	 */
	synchronized static private void loadProps() {
		String result = getCity();
		try {
			prop.load(new InputStreamReader(
					PropertiesUtil.class.getClassLoader().getResourceAsStream("/city/" + result + ".properties"),
					"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据配置文件key值，或者value
	 * 
	 * @param key：配置文件key值
	 * @return
	 */
	public static String getProperty(String key) {
		if (null == prop) {
			loadProps();
		}
		return prop.getProperty(key);
	}

	/**
	 * 根据配置文件key值，或者value
	 * 
	 * @param key：配置文件key值
	 * @param defaultValue：默认值
	 * @return
	 */
	public static String getProperty(String key, String defaultValue) {
		if (null == prop) {
			loadProps();
		}
		return prop.getProperty(key, defaultValue);
	}

	/**
	 * 获取动态加载的Properties对象
	 * 
	 * @return Properties对象
	 */
	public static Properties getProperties() {
		return prop;
	}

	/**
	 * 根据地市局代码，动态读取配置文件
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	synchronized static public String getCity() {
		try {
			SAXReader reader = new SAXReader();
			InputStream inputStream = PropertiesUtil.class.getResourceAsStream("/city/city.xml");
			document = reader.read(inputStream);
		} catch (DocumentException e) {
			System.out.println("解释xml出错!");
			e.printStackTrace();
		}
		if (!StringUtils.isEmpty("")) {
			System.out.println("地市代码为空，无法获取配置文件信息。");
		}
		String result = "";
		Element root = document.getRootElement();
		List<Element> nodes = root.elements("city");
		for (Object obj : nodes) {
			Element e = (Element) obj;
			String cityCode = e.attributeValue("cityCode");
			if (configCityCode.equals(cityCode)) {
				result = e.getText();
				System.out.println("获取配置文件成功，地市代码:" + cityCode + "   地市名称：" + e.attributeValue("name") + "   文件名："
						+ result + ".properties");
			}
		}
		if (StringUtils.isEmpty(result)) {
			System.out.println("地市代码错误，无法获取配置文件信息，请检查dbconfig.properties配置文件的config.city.code参数......");
		}
		return result;
	}

}
