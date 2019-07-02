package util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class JacksonUtil {
	/**
	 * 
	 */
	private JacksonUtil() {
	}

	/**
	 *
	 */
	private static final MyObjectMapper mapper = new MyObjectMapper();

	/**
	 *
	 * @return
	 */
	public static ObjectMapper getInstance() {

		return mapper;
	}

	public static <T> T fromJson(String json, Class<?> class1) {
		try {
			@SuppressWarnings("unchecked")
			T cls = (T) getInstance().readValue(json, class1);
			return cls;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取泛型的Collection Type
	 *
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	public static <T> List<T> convertList(String jsonString, Class<T> clazz) {
		if (jsonString != null && !"".equals(jsonString)) {
			JavaType javaType = getCollectionType(ArrayList.class, clazz);
			List<T> list = null;
			try {
				list = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return list;
		}

		return null;
	}

	public static String toJson(Object obj) {
		try {

			// mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
			// false);

			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static <T> String toJsonFilfterIgnore(Object obj, Class<?> target, Class<?> mixinSource, String... filterFields) {
		try {
			SimpleFilterProvider sp = new SimpleFilterProvider();
			FilterProvider filters = sp.addFilter("userFilter", SimpleBeanPropertyFilter.serializeAllExcept(filterFields));

			// FilterProvider filters = new
			// SimpleFilterProvider().addFilter(clazz.getName(),
			// SimpleBeanPropertyFilter.serializeAllExcept(filterFields));
			mapper.addMixInAnnotations(target, mixinSource);
			mapper.setFilters(filters);
			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static <T> String toJsonFilfterWant(Object obj, Class<?> target, Class<?> mixinSource, String... filterFields) {
		try {
			SimpleFilterProvider sp = new SimpleFilterProvider();
			FilterProvider filters = sp.addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(filterFields));

			// FilterProvider filters = new
			// SimpleFilterProvider().addFilter(clazz.getName(),
			// SimpleBeanPropertyFilter.serializeAllExcept(filterFields));
			mapper.addMixInAnnotations(target, mixinSource);
			mapper.setFilters(filters);
			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Map fromJsonToMap(String json){
		Map m = null;
		try {
			m = mapper.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m;
	}
}