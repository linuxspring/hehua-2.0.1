package myshiro.filter;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/7/30.
 * IntelliJ IDEA 2017 of gzcss
 */
public class KickoutSessionControlFilter extends AccessControlFilter {

    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1
    private SessionManager sessionManager;
    private String kickoutUrl; //踢出后到的地址

    private Cache<String, Deque<Serializable>> cache;

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String url2 = httpRequest.getRequestURL().toString();
        System.out.println("url2 = " + url2);
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            HttpServletRequest req= (HttpServletRequest) request;
            String url =req.getQueryString();
            //((HttpServletResponse)response).sendRedirect(kickoutUrl);
            if (isAjax((HttpServletRequest) request)) {
                ((HttpServletResponse)response).setHeader("sessionstatus", "timeout");
                ((HttpServletResponse)response).sendError(518, "session timeout.");
            }else{
                //这里可以做登录
            }
            return false;
        }

        Session session = subject.getSession();
        String username = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();
        Deque<Serializable> deque = cache.get(username);
        if (deque == null) {
            deque = new LinkedList<Serializable>();
            cache.put(username, deque);
        }
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);

        }
        while ((deque.size() > maxSession)) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) {
                kickoutSessionId = deque.removeFirst();
            } else {
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (SessionException e) {
                //已经踢出
                //e.printStackTrace();
            }
        }

        if (session.getAttribute("kickout") != null) {
            try {
                subject.logout();
            } catch (Exception e) {
                e.printStackTrace();
            }
            saveRequest(request);
            WebUtils.issueRedirect(request, response, kickoutUrl);
            return false;
        }

        return true;
    }

    boolean isAjax(HttpServletRequest request){
        String isAjaxRequest= request.getHeader("isAjax");
        return  ((request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())||"isAjax-json".equals(isAjaxRequest))   ) ;
    }
}
