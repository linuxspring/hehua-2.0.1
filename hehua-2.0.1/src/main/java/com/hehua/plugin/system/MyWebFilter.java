package com.hehua.plugin.system;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Order(1)
@Configuration
@WebFilter(filterName = "myWebFilter", urlPatterns = {"/*"})
public class MyWebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

		response.setContentType("textxml;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, Accept, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,isajax,iaAjax");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("XDomainRequestAllowed","1");
        //response.setContentType("application/json; charset=utf-8");
        //response.setCharacterEncoding("UTF-8");
        String queryString = request.getQueryString();
        System.out.println("url = " + queryString);
        System.out.println("request.getHead('Origin') = " + request.getHeader("Origin"));


        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    /**权限校验*/
    private boolean checkAuthority() {
        //TODO 这里进行权限检查

        return true;
    }


}
