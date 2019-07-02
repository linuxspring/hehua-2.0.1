package util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * DES加解密工具类
 * 
 * @author heyaonan
 *
 */
public class DESUtil {
	private static final String DES_ALGORITHM = "DES";

	/**
	 * DES加密
	 * 
	 * @param plainData 原始字符串
	 * @param secretKey 加密密钥
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String encryption(String plainData, String secretKey) throws Exception {

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {

		}

		try {
			// 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must
			// be multiple of 8 when decrypting with padded cipher异常，
			// 不能把加密后的字节数组直接转换成字符串
			byte[] buf = cipher.doFinal(plainData.getBytes());

			return Base64Utils.encode(buf);

		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("BadPaddingException", e);
		}

	}


	/**
	 * DES解密
	 * @param secretData 密码字符串 解密密钥
	 * @param
	 * @return 原始字符串
	 * @throws Exception
	 */
	public static String decryption(String secretData) {
		String secretKey = "ovATL3QOQmKh0WiTqhkSbg==";
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));
		} catch (Exception e) {
			return secretData;
		} 

		try {
			//String res = new String(secretData.getBytes("utf-8"));
			byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));
			return new String(buf);
		} catch (Exception e) {
			return secretData;
		} 
	}

	/**
	 * 获得秘密密钥
	 * 
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 */
	private static SecretKey generateKey(String secretKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
		DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
		keyFactory.generateSecret(keySpec);
		return keyFactory.generateSecret(keySpec);
	}

	public static String decodeBase64(String content) throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(content)) {
			return "";
		}
		String userNameBase64=content;
		byte[] userNameByte = Base64.decodeBase64(userNameBase64.getBytes("UTF-8"));
		String userName = new String(userNameByte,"UTF-8");//获得解密后的用户名
		return userName;
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j)) {
				tmp.append(j);
			}
			else if (j < 256) {
				tmp.append("%");
				if (j < 16) {
					tmp.append("0");
				}
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}


	static private class Base64Utils {

		static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
				.toCharArray();
		static private byte[] codes = new byte[256];

		static {
			for (int i = 0; i < 256; i++) {
				codes[i] = -1;
			}
			for (int i = 'A'; i <= 'Z'; i++) {
				codes[i] = (byte) (i - 'A');
			}
			for (int i = 'a'; i <= 'z'; i++) {
				codes[i] = (byte) (26 + i - 'a');
			}
			for (int i = '0'; i <= '9'; i++) {
				codes[i] = (byte) (52 + i - '0');
			}
			codes['+'] = 62;
			codes['/'] = 63;
		}

		/**
		 * 将原始数据编码为base64编码
		 */
		static private String encode(byte[] data) {
			char[] out = new char[((data.length + 2) / 3) * 4];
			for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
				boolean quad = false;
				boolean trip = false;
				int val = (0xFF & data[i]);
				val <<= 8;
				if ((i + 1) < data.length) {
					val |= (0xFF & data[i + 1]);
					trip = true;
				}
				val <<= 8;
				if ((i + 2) < data.length) {
					val |= (0xFF & data[i + 2]);
					quad = true;
				}
				out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
				val >>= 6;
				out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
				val >>= 6;
				out[index + 1] = alphabet[val & 0x3F];
				val >>= 6;
				out[index + 0] = alphabet[val & 0x3F];
			}

			return new String(out);
		}

		/**
		 * 将base64编码的数据解码成原始数据
		 */
		static private byte[] decode(char[] data) {
			int len = ((data.length + 3) / 4) * 3;
			if (data.length > 0 && data[data.length - 1] == '=') {
				--len;
			}
			if (data.length > 1 && data[data.length - 2] == '=') {
				--len;
			}
			byte[] out = new byte[len];
			int shift = 0;
			int accum = 0;
			int index = 0;
			for (int ix = 0; ix < data.length; ix++) {
				int value = codes[data[ix] & 0xFF];
				if (value >= 0) {
					accum <<= 6;
					shift += 6;
					accum |= value;
					if (shift >= 8) {
						shift -= 8;
						out[index++] = (byte) ((accum >> shift) & 0xff);
					}
				}
			}
			//            if (index != out.length)
				//throw new Error("miscalculated data length!");
			return out;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String b = DESUtil.decryption("ea0fb61b-3a38-4055-bef1-8f99657f9f6c");
		System.out.println(b);
		String a = DESUtil.encryption("a", "gzcss");
	}
}




