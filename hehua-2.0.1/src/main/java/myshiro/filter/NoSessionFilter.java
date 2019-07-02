package myshiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/16.
 * IntelliJ IDEA 2017 of gzcss
 */
public class NoSessionFilter extends NoSessionCreationFilter {

    public NoSessionFilter() {
        super();
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String username = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();
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
        return super.onPreHandle(request, response, mappedValue);
    }
    boolean isAjax(HttpServletRequest request){
        String isAjaxRequest= request.getHeader("isAjax");
        return  ((request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())||"isAjax-json".equals(isAjaxRequest))   ) ;
    }
}
