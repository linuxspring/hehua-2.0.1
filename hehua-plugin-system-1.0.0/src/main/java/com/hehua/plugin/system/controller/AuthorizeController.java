package com.hehua.plugin.system.controller;

import com.hehua.plugin.system.service.Impl.ClientServiceImpl;
import com.hehua.plugin.system.service.Impl.OAuthServiceImpl;
import myshiro.PasswordHelper;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import util.AjaxJson;
import util.Constants;
import util.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class AuthorizeController {

	@Autowired
	private OAuthServiceImpl oAuthService;
	@Autowired
	private ClientServiceImpl clientService;

	@RequestMapping(value="/authorize.data")//,produces="text/html;charset=UTF-8"
	public Object authorize(Model model, HttpServletRequest request,HttpServletResponse resp) throws URISyntaxException, OAuthSystemException, UnsupportedEncodingException {

		try {
			// 构建OAuth 授权请求
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

			// 检查传入的客户端id是否正确
			if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT)
						.setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION).buildJSONMessage();
				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}
			request.setAttribute("error", "");
			Subject subject = SecurityUtils.getSubject();
			// 如果用户没有登录，跳转到登陆页面
			if (!subject.isAuthenticated()) {
				if (!login(subject, request)) {// 登录失败时跳转到登陆页面
					model.addAttribute("client", clientService.findByClientId(oauthRequest.getClientId()));
					model.addAttribute("error", "登录失败时跳转到登陆页面");

					Object planId =  request.getAttribute("code");
					if (planId == null) {
						System.out.println("planId = " + planId);
					}else{
						System.out.println("planId = " + planId);
					}
					resp.setCharacterEncoding("UTF-8");
					resp.setContentType("text/html;charset=UTF-8");
					return new ModelAndView("login").addObject("code",planId).addObject("error", "登录失败时跳转到登陆页面");
					//return "login";
				}
			}
			System.out.println("request = " + request.getHeader("Origin"));
			String username = (String) subject.getPrincipal();
			// 生成授权码
			String authorizationCode = null;
			// responseType目前仅支持CODE，另外还有TOKEN
			String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			if (responseType.equals(ResponseType.CODE.toString())) {
				OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
				authorizationCode = oauthIssuerImpl.authorizationCode();
				oAuthService.addAuthCode(authorizationCode, username);
			}

			// 进行OAuth响应构建
			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request,
					HttpServletResponse.SC_FOUND);
			// 设置授权码
			builder.setCode(authorizationCode);
			// 得到到客户端重定向地址
			String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

			// 构建响应
			final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();

			// 根据OAuthResponse返回ResponseEntity响应
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));
			return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
		} catch (OAuthProblemException e) {

			// 出错处理
			String redirectUri = e.getRedirectUri();
			if (OAuthUtils.isEmpty(redirectUri)) {
				// 告诉客户端没有传入redirectUri直接报错
				return new ResponseEntity("OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
			}

			// 返回错误消息（如?error=）
			final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e)
					.location(redirectUri).buildQueryMessage();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));
			return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
		}
	}

	private boolean login(Subject subject, HttpServletRequest request) {
		if ("get".equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usernamePost = request.getParameter("usernamePost");
		String md5 = new Md5Hash(password, null, 2).toString();
		String shaStr1 = new Sha256Hash("123456", null).toString();
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return false;
		}
		String cityCode ="03030034";// PropertiesUtil.getProperty("config.cityCode");
		 
		if(cityCode.equals("030300")){
			password=PasswordHelper.encrypt(username, password, PasswordHelper.getStaticSalt());
		}
		//password=md5;
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		String pwdnum = "pwdnum";
		if (oAuthService.isLocked(username + pwdnum)) {
			System.out.println("用户密码输入错误超过5次，用户被锁定");
			request.setAttribute("error", "登录失败:" + "用户密码输入错误超过5次，用户被锁定");
			request.setAttribute("code", 2);
			return false;
		}
		try {
			if (oAuthService.checkUserIsExits(username,username+usernamePost) == 0) {
				request.setAttribute("error", "登录失败:" + "用户不存在，请重新输入");
				request.setAttribute("code", 1);
				return false;
			}
			request.getSession().setAttribute("isAjax", "true");
			subject.login(token);
			return true;
		} catch (Exception e) {
			// 利用Ehcache记录用户登陆次数
			oAuthService.doLimitMatch(username + pwdnum);
			System.out.println("登录失败，用户与密码不一致:" + e.getClass().getName());
			request.setAttribute("error", "登录失败:用户与密码不一致");
			request.setAttribute("code", 0);
			return false;
		}
	}

	@RequestMapping(value = "/ajaxLogin.data",method = RequestMethod.POST)
	@ResponseBody
	public String loginAjax(String username, String password,String usernamePost) {
		AjaxJson ajaxJson = new AjaxJson();
		String md5 = new Md5Hash(password, null, 2).toString();
		String shaStr1 = new Sha256Hash("123456", null).toString();
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			ajaxJson.setSuccess( false);
		}
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			String pwdnum = "pwdnum";
			if (oAuthService.isLocked(username + pwdnum)) {
				System.out.println("用户密码输入错误超过5次，用户被锁定");
				//request.setAttribute("error", "登录失败:" + "用户密码输入错误超过5次，用户被锁定");
				ajaxJson.setSuccess(false);
			}

			try {
				if (oAuthService.checkUserIsExits(username, username + usernamePost) == 0) {
					//request.setAttribute("error", "登录失败:" + "用户不存在，请重新输入");
					ajaxJson.setSuccess(false);
				}
				//request.getSession().setAttribute("isAjax", "true");
				subject.login(token);
				ajaxJson.setSuccess(true);
			} catch (Exception e) {
				// 利用Ehcache记录用户登陆次数
				oAuthService.doLimitMatch(username + pwdnum);
				System.out.println("登录失败，用户与密码不一致:" + e.getClass().getName());
				//request.setAttribute("error", "登录失败:用户与密码不一致");
				ajaxJson.setSuccess(false);
			}
		}
		String text =ajaxJson.getJsonStr();
		return  text;
	}

}