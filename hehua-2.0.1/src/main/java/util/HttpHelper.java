package util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/14.
 * IntelliJ IDEA 2017 of gzcss
 */
public class HttpHelper {
    public final static String USER_COOKIE = "cookie_user";

    @Deprecated
    public final static String SJSSIONID = "SJSSIONID";

    @Deprecated
    public final static String USER_COOKIE_NAME = "user_name";

    public final static int COOKIE_TIME = 2592000; // 秒，相当于30天
    /**
     * @param response
     * @param key
     * @param value
     * @param seconds  保存的时间（秒），如果不想设置，请把seconds设置为<=0
     */
    public static void addCookie(HttpServletResponse response, String key, String value, int seconds) {

        Cookie cookie = new Cookie(key, value);
        if (seconds > 0) {
            cookie.setMaxAge(seconds);
        }
        addCookie(response, cookie);
    }

    public static void setCookie(HttpServletRequest request,HttpServletResponse response,String key,String value,String domain,String path){
        Cookie cookie = getCookie(request, key);
        if(null == cookie){
            cookie = new Cookie(key, value);
        }
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setValue(value);
        cookie.setMaxAge(COOKIE_TIME);
        addCookie(response, cookie);
    }

    /**
     * @param response
     * @param cookie
     */
    public static void addCookie(HttpServletResponse response, Cookie cookie) {

        response.addCookie(cookie);
    }

    /**
     *
     * @param request
     * @param response
     * @param key
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String key) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(key, cookie.getName())) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    /**
     * @param request
     * @param key
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie cookie = getCookie(request, key);
        return null != cookie ? cookie.getValue() : "";
    }

    public static Cookie getCookie(HttpServletRequest request,String key){
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(key, cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param map
     * @param response
     */
    public static void addCookies(HttpServletResponse response, Map<String, String> map, int seconds) {
        for (String key : map.keySet()) {
            addCookie(response, key, map.get(key), seconds);
        }
    }

}
