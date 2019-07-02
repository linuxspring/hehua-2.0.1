package com.hehua.plugin.system.controller;

import com.hehua.plugin.system.service.Impl.OAuthServiceImpl;
import util.AjaxJson;
import util.DESUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/17.
 * IntelliJ IDEA 2017 of gzcss
 */
@Controller
@RequestMapping("wechat")
public class WechatController {
    @Autowired
    private OAuthServiceImpl oAuthService;

    @RequestMapping(value = "/oauth2.data",method = RequestMethod.POST)
    @ResponseBody
    public String oauth2(){
        Subject subject = SecurityUtils.getSubject();
        String username=(String)subject.getPrincipal();
        System.out.println(username);
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setSuccess(true);
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        ajaxJson.setObj(map);
        ajaxJson.setMsg("取得用户成功");
        String text=ajaxJson.getJsonStr();
        return text;//JSONObject.fromObject(ajaxJson.getJsonStr());
    }

    @RequestMapping(value = "sysLogout.data",method = RequestMethod.GET)
    @ResponseBody
    public String logout(HttpServletRequest request){
        AjaxJson ajaxJson = new AjaxJson();
        try {
            Subject subject=SecurityUtils.getSubject();
            String pwdnum = "pwdnum";
            String username =(String) subject.getPrincipal();
            subject.logout();
            subject.getSession().stop();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("rememberMe")) {
                        break;
                    }
                }
            }
            oAuthService.removeRetary(username+pwdnum);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("退出成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setMsg("退出失败");
            ajaxJson.setSuccess(false);
        }
        String text =ajaxJson.getJsonStr();
        return text;
        //return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/login.jsp";
    }
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    @ResponseBody
    public void logoutWechat(HttpServletRequest request){
        AjaxJson ajaxJson = new AjaxJson();
        try {
            Subject subject=SecurityUtils.getSubject();
            subject.logout();
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("退出成功");
            //InternalResourceViewResolver.REDIRECT_URL_PREFIX + "http://localhost:8080/wxtest/logout";
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setMsg("退出失败");
            ajaxJson.setSuccess(false);
        }
        String text =ajaxJson.getJsonStr();
        //return text;
        //return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/login.jsp";
    }

    @RequestMapping(value="/token.data",method = RequestMethod.GET)
    public void token(HttpServletRequest request, HttpServletResponse response) {
        String service = WebUtils.getCleanParam(request, "service");
        String rememberMe =null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("rememberMe")) {
                rememberMe=cookies[i].getValue();
                break;
            }
        }
        //SecurityUtils.getSubject().get(request, "rememberMe");
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            System.out.println("0 = " + 0);
        }
        Session session = SecurityUtils.getSubject().getSession();
        SecurityUtils.getSubject().getSession().setAttribute("hasSyn", true);
        SecurityUtils.getSubject().getSession().setAttribute("meto", "xiaotolove");
        request.getSession().setAttribute("hasSyn",true);
        session.setAttribute("hasSyn", true);   //将hasSyn为true设置到session中，CasFilter会从Session中取该值
        if (StringUtils.isEmpty(rememberMe)) {
            rememberMe="";
            try {
                //WebUtils.saveRequest(request);
                //response.sendRedirect(service+"?jsid="+session.getId()+"&hasSyn="+true+"&rememberMe="+rememberMe);

                WebUtils.issueRedirect(request, response, service+"?jsid="+session.getId()+"&hasSyn="+true+"&rememberMe="+rememberMe);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{

        }
    }

    @Description("提交调查单")
    @RequestMapping(value = "/wtoken.data",method = RequestMethod.GET)
    @ResponseBody
    public void saveToken(HttpServletRequest request, HttpServletResponse response,@RequestParam("username") String username) throws Exception {
        String accessToken="gzcss";
        String key ="gzcss_client";
        String currentlogincookie = "{'username':'" + username + "','token':'"+accessToken+"','key':'"+key+"'}";
        System.out.println(currentlogincookie + "****************");
        String secretKey = "ovATL3QOQmKh0WiTqhkSbg==";
        currentlogincookie= DESUtil.encryption(currentlogincookie, secretKey);
        String tt = DESUtil.decryption(currentlogincookie);
        Cookie cookie = new Cookie("qixinlogincookie", URLEncoder.encode(currentlogincookie, "UTF-8"));
        cookie.setMaxAge(1800);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}