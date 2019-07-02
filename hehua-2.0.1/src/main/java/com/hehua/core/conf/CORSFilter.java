//package com.hehua.core.conf;
//
//import org.springframework.boot.web.servlet.ServletComponentScan;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @createTime 2017-09-06
// * @author pengguojin
// * @desc 解决跨域文件
// * @project itsm工程
// *
// */
//@Component
//@ServletComponentScan
//@WebFilter(urlPatterns = "/*",filterName = "shiroLoginFilter")
//public class CORSFilter implements Filter {
//
//	private FilterConfig config = null;
//	@Override
//	public void init(FilterConfig config) throws ServletException {
//		this.config = config;
//	}
//	@Override
//	public void destroy() {
//		this.config = null;
//	}
//
//
////	@Override
////	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
////			throws IOException, ServletException {
////		HttpServletResponse response = (HttpServletResponse) res;
////		HttpServletRequest request = (HttpServletRequest) req;
//////		response.setHeader("Access-Control-Allow-Origin", "*");
//////		response.setHeader("Access-Control-Allow-Credentials", "true");
//////		//response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//////		//response.setHeader("Access-Control-Allow-Headers", "Authentication");
//////		response.setHeader("Access-Control-Allow-Methods", "*");
//////		//response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//////		response.setHeader("Access-Control-Max-Age", "3600");
//////		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
////
////		response.setContentType("textml;charset=UTF-8");
////		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
////		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
////		response.setHeader("Access-Control-Max-Age", "0");
////		response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, Accept, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,isAjax");
////		response.setHeader("Access-Control-Allow-Credentials", "true");
////		response.setHeader("XDomainRequestAllowed","1");
////		//System.out.println("request.getHead('Origin') = " + request.getHeader("Origin"));
////		chain.doFilter(req, res);
////	}
//
//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//		HttpServletResponse response = (HttpServletResponse) servletResponse;
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
//		// 允许哪些Origin发起跨域请求,nginx下正常
//		// response.setHeader( "Access-Control-Allow-Origin", config.getInitParameter( "AccessControlAllowOrigin" ) );
//		response.setHeader( "Access-Control-Allow-Origin", "*" );
//		// 允许请求的方法
//		response.setHeader( "Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT" );
//		// 多少秒内，不需要再发送预检验请求，可以缓存该结果
//		response.setHeader( "Access-Control-Max-Age", "3600" );
//		// 表明它允许跨域请求包含xxx头
//		response.setHeader( "Access-Control-Allow-Headers", "x-auth-token,Origin,Access-Token,X-Requested-With,Content-Type, Accept" );
//		//是否允许浏览器携带用户身份信息（cookie）
//		response.setHeader( "Access-Control-Allow-Credentials", "true" );
//		// response.setHeader( "Access-Control-Expose-Headers", "*" );
//		if (request.getMethod().equals( "OPTIONS" )) {
//			response.setStatus( 200 );
//			return;
//		}
//		filterChain.doFilter( servletRequest, response );
//	}
//
//}
