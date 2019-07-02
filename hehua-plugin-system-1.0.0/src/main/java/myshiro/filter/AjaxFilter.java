package myshiro.filter;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import util.AjaxJson;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/19.
 * IntelliJ IDEA 2017 of gzcss
 */
public class AjaxFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String url2 = httpRequest.getRequestURL().toString();
        System.out.println("url2 = " + url2);
        Subject subject = SecurityUtils.getSubject();
        String username=(String)subject.getPrincipal();
        if(this.isLoginRequest(request, response)) {
            if(this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {


                return true;
            }
        } else {
            if(isAjax(request)){
                Map<String,Object> result=new HashMap<String,Object>();
                result.put("isLogin",false);
                AjaxJson ajaxJson = new AjaxJson();
                ajaxJson.setSuccess(false);
                String text ="{'isLogin':false}";
                response.getWriter().print(text);
            }else{
                //this.saveRequestAndRedirectToLogin(request, response);
                return true;
            }
            //return false;
            return true;
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("======================="+subject.getPrincipal());
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        System.out.println("token======================="+token.getPrincipal());
        return super.onLoginFailure(token, e, request, response);
    }

    public static boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        String isAjaxRequest= ((HttpServletRequest) request).getHeader("isAjax");
        if("XMLHttpRequest".equalsIgnoreCase(header)||"isAjax-json".equals(isAjaxRequest)){
            System.out.println( "当前请求为Ajax请求");
            return Boolean.TRUE;
        }
        System.out.println(  "当前请求非Ajax请求");
        return Boolean.FALSE;
        //HttpServletRequest request2=((HttpServletRequest) request);
        //String isAjaxRequest= request2.getHeader("isAjax");
        //return  ((request2.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request2.getHeader("X-Requested-With").toString())||"isAjax-json".equals(isAjaxRequest))   ) ;
    }


}
