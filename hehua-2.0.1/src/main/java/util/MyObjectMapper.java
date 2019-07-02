package util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyObjectMapper extends ObjectMapper {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings("deprecation")
	public MyObjectMapper() {
		super();
		this.setDateFormat(dateFormat);
		// this.getSerializationConfig().setDateFormat(dateFormat);

		// this.disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS); //
		// 避免IE执行AJAX时,返回JSON出现下载文件
		// 允许单引号
		this.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);

		// 字段和值都加引号
		// this.configure(JsonGenerator.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
		// true);
		// 数字也加引号
		// this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		// this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS,
		// true);
		// 空值处理为空串
		// 属性为null时输出空字符串
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object obj, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {

				jg.writeString("");
			}

			public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws JsonParseException, IOException {
				String str = jsonParser.getText().trim();
				try {
					return dateFormat.parse(str);
				} catch (Exception e) {
					// TODO: handle exception
				}
				return context.parseDate(str);
			}
		});

	}
}
