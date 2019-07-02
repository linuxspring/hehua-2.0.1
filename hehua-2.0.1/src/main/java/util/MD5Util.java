package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 作者 pengguojin:
 * @createTime 创建时间：2017年9月18日 上午9:31:50
 * @version 版本 1.0
 * @desc 描述 MD5加密解密
 * @project ITSM后台服务
 */
public class MD5Util {
	static private char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	/**
	 * 取得指定字符串的MD5加密摘要
	 * 
	 * @param str
	 * @return
	 */
	public static String digest(String str) {
		MessageDigest mdInst = null;
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

		}

		mdInst.update(str.getBytes());
		// 获得密文
		byte[] md = mdInst.digest();
		// 把密文转换成十六进制的字符串形式
		int j = md.length;
		char str1[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str1[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str1[k++] = hexDigits[byte0 & 0xf];
		}

		return new String(str1);
	}

	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = (md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	// 可逆的加密算法
	public static String KL(String inStr) {
		// String s = new String(inStr);
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	// 加密后解密
	public static String JM(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}

	// 测试主函数
	public static void main(String args[]) {

		String s = new String("a");
		System.out.println("原始：" + s);
		System.out.println("MD5后：" + MD5(s));
		System.out.println("MD5后再加密：" + KL(MD5(s)));
		System.out.println("解密为MD5后的：" + JM(KL(MD5(s))));
	}
}
