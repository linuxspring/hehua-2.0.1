package myshiro.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import util.DESUtil;
import util.HttpHelper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;

public class QixinFilter  extends AdviceFilter {
    private final static Logger log = LoggerFactory.getLogger(QixinFilter.class);

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String redirectUrl;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response)throws Exception {
        Subject subject= SecurityUtils.getSubject();
        if (subject != null&&subject.isAuthenticated()) {
            return true;
        }

        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String url = httpRequest.getRequestURL().toString();
        log.info("url=" + url);
        if (url.endsWith("wtoken.data")) {
            return true;
        }
        if (!url.endsWith(".do") && !url.endsWith(".data")) {
            return true;
        }

        String token = WebUtils.getCleanParam(request, "token");
        String key = WebUtils.getCleanParam(request, "key");
        if(StringUtils.isEmpty(token)||StringUtils.isEmpty(key)){
            return false;
        }

        String cookieJson= HttpHelper.getCookieValue(WebUtils.toHttp(request), "qixinlogincookie");
        if (StringUtils.isEmpty(cookieJson)) {
            return false;
        }
        String tt=URLDecoder.decode(cookieJson, "UTF-8");
        String cookieJson2 = DESUtil.decryption(tt);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        Map<String,String> map=mapper.readValue(cookieJson2, Map.class);//JacksonUtil.fromJsonToMap(cookieJson2);
        if (token.equals(map.get("token").toString()) && key.equals(map.get("key").toString())) {
            return  true;
        }
        WebUtils.issueRedirect(request, response, redirectUrl);
        log.info("redirect : " + url);
        return false;
    }
}
