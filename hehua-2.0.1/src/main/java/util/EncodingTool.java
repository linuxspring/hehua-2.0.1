package util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author pengguojin
 * @createTime 2017-09-01
 * @desc 乱码转换工具
 * @project itsm工程
 *
 */
public class EncodingTool {
	public static String encodeStr(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
