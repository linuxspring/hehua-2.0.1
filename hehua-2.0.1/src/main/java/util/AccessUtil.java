package util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2017/7/11. IntelliJ IDEA 2017 of gzcss
 */
public class AccessUtil {
	public static HashMap<String, ClientBaseInfo> _hashMapClient = new HashMap<String, ClientBaseInfo>();
	public static HashMap<String, String> tokenMap = new HashMap<String, String>();
	// private static final tokenKey = "gzcss";

	public static boolean isOk(HttpServletRequest request, HttpServletResponse response, String accessToken, String key,
			String appid) {
		String token = DESUtil.decryption(accessToken);
		System.out.println("tokenMap" + tokenMap);
		if (tokenMap.containsKey(token)) {
			ClientBaseInfo oldClient = _hashMapClient.get(token);
			ClientBaseInfo newClient = getClient(request);
			// 不拦截消息推送
			if (StringUtils.isEmpty(appid)) {
				System.out.println("appid为空，进来了");
				boolean issame = isSameBrower(newClient, oldClient);
				if (!issame) {
					return false;
				}
				boolean isLose = isLosingTime(new Date(), oldClient.loginDateTime);
				if (!isLose) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public static boolean isOkWechat(String accessToken, String key) {
		String token = accessToken;// DESUtil.decryption(accessToken);
		if (tokenMap.size() == 0) {
			return true;
		}
		if (tokenMap.containsKey(token)) {
			ClientBaseInfo clientBaseInfo = _hashMapClient.get(token);
			clientBaseInfo.loginDateTime = new Date();
			_hashMapClient.put(token, clientBaseInfo);
			return true;
		}
		return false;
	}

	public static TokenBean create(HttpServletRequest request, String username) {
		TokenBean bean = new TokenBean();
		bean.setTimestamp(7200);
		bean.setKey("gzcss");
		bean.setAccessToken(UUID.randomUUID().toString());
		String addr = request.getRemoteAddr();
		String remoteUser = request.getRemoteUser();
		String agent = request.getHeader("user-agent");
		ClientBaseInfo client = getClient(request);

		// System.out.println("Accept: " + request.getHeader("Accept"));
		// System.out.println("Host: " + request.getHeader("Host"));
		// System.out.println("Referer : " + request.getHeader("Referer"));
		// System.out.println("Accept-Language : " +
		// request.getHeader("Accept-Language"));
		// System.out.println("Accept-Encoding : " +
		// request.getHeader("Accept-Encoding"));
		// System.out.println("User-Agent : " + request.getHeader("User-Agent"));
		// System.out.println("Connection : " + request.getHeader("Connection"));
		// System.out.println("Cookie : " + request.getHeader("Cookie"));

		if (tokenMap.containsValue(username)) {
			Iterator<String> its = tokenMap.keySet().iterator();
			while (its.hasNext()) {
				String tkey = its.next();
				if (username.equals(tokenMap.get(tkey))) {
					its.remove();
				}
			}

			tokenMap.put(bean.getAccessToken(), username);
		} else {
			tokenMap.put(bean.getAccessToken(), username);
		}
		// String username="liyongjie";

		_hashMapClient.put(bean.getAccessToken(), client);
		return bean;
	}

	public static ClientBaseInfo getClient(HttpServletRequest request) {
		ClientBaseInfo client = new ClientBaseInfo();
		client.Accept = request.getHeader("Accept");
		client.Host = request.getHeader("Host");
		client.Referer = request.getHeader("Referer");
		client.Accept_Encoding = request.getHeader("Accept-Encoding");
		client.Accept_Language = request.getHeader("Accept-Language");
		client.User_Agent = request.getHeader("User-Agent");
		client.Cookie = request.getHeader("Cookie");
		client.Connection = request.getHeader("Connection");
		return client;
	}

	public static boolean isSameBrower(ClientBaseInfo newclient, ClientBaseInfo oldclient) {
		boolean flag = true;
		if (!StringUtils.isEmpty(oldclient.Accept_Language) && !StringUtils.isEmpty(newclient.Accept_Language)) {
			if (!oldclient.Accept_Language.equals(newclient.Accept_Language)) {
				flag = false;
			}
		}
		if (!oldclient.Accept.equals(newclient.Accept)) {
			// flag=false;
		}
		if (!oldclient.Connection.equals(newclient.Connection)) {
			flag = false;
		}
		if (!oldclient.Accept_Encoding.equals(newclient.Accept_Encoding)) {
			if (newclient.Accept_Encoding.indexOf(oldclient.Accept_Encoding) != -1) {
				flag = false;
			}
		}
		if (!oldclient.User_Agent.equals(newclient.User_Agent)) {
			flag = false;
		}
		if (!oldclient.Referer.equals(newclient.Referer)) {
			// flag=false;
		}
		if (!oldclient.Host.equals(newclient.Host)) {
			flag = false;
		}
		if (!StringUtils.isEmpty(newclient.Cookie) && !StringUtils.isEmpty(oldclient.Cookie)) {
			if (!oldclient.Cookie.equals(newclient.Cookie)) {
				flag = false;
			}
		}
		return flag;
	}

	public static boolean isLosingTime(Date nowTime, Date loginTime) {
		long time = (nowTime.getTime() - loginTime.getTime()) / 1000 / 60;
		if (time > 30) {
			return false;
		}
		return true;
	}

	public static boolean releaseTime(Date nowTime, Date loginTime) {
		long time = (nowTime.getTime() - loginTime.getTime()) / 1000 / 60;
		if (time > 120) {
			return true;
		}
		return false;
	}

//	public static String getUser(ActionContext ctx) {
//		Map<String, Object> whereSession = ctx.getSession();
//		// 取出名为user的Session属性
//		String user = (String) whereSession.get("user");
//		return user;
//	}
	
	public static void removeToken() {
		tokenMap.clear();
	}

}
