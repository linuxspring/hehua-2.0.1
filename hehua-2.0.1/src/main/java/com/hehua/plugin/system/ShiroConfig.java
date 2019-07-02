package com.hehua.plugin.system;

import myshiro.UserRealm;
import myshiro.filter.KickoutSessionControlFilter;
import myshiro.filter.MyFormAuthenticationFilter;
import myshiro.filter.ShiroUserFilter;
import myshiro.filter.SysUserFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wzy
 */
@Configuration
public class ShiroConfig {


    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public Filter getSysUserFilter(){
        return new SysUserFilter();
    }

    @Bean
    public Filter kickoutSessionControlFilter(){
        KickoutSessionControlFilter kickoutFilter=new KickoutSessionControlFilter();
        kickoutFilter.setCacheManager(ehCacheManager());
        kickoutFilter.setKickoutAfter(false);
        kickoutFilter.setMaxSession(2);
        kickoutFilter.setSessionManager(sessionManager());
        kickoutFilter.setKickoutUrl("/login.jsp?kickout=1");
        return kickoutFilter;
    }

    @Bean
    public Filter formAuthenticationFilter(){
        FormAuthenticationFilter formFitler=new FormAuthenticationFilter();
        formFitler.setUsernameParam("username");
        formFitler.setPasswordParam("password");
        formFitler.setRememberMeParam("rememberMe");
        formFitler.setLoginUrl("/login.jsp");
        return formFitler;
    }

    @Bean
    public Filter userFilter(){
        return new ShiroUserFilter();
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(){
        System.out.println("***************************ShiroConfiguration.shirFilter()**************");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/usersPage");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        Map<String, Filter> map = new HashMap<>();
        map.put("userFilter", userFilter());
        map.put("authc", formAuthenticationFilter());
        map.put("sysUser", getSysUserFilter());
        map.put("kickout", kickoutSessionControlFilter());

        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/index.jsp","anon");
        filterChainDefinitionMap.put("/index.html","anon");
        filterChainDefinitionMap.put("/validcode","anon");
        filterChainDefinitionMap.put("/statics/*","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/ref/**","anon");
        filterChainDefinitionMap.put("/resources/**","anon");
        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //自定义加载权限资源关系
//        List<Resources> resourcesList = resourcesService.queryAll();
//        for(Resources resources:resourcesList){
//
//            if (StringUtils.isNotEmpty(resources.getResurl())) {
//                String permission = "perms[" + resources.getResurl()+ "]";
//                filterChainDefinitionMap.put(resources.getResurl(),permission);
//            }
//        }

        filterChainDefinitionMap.put("/authorize.data*","anon");
        filterChainDefinitionMap.put("/accessToken.data*","anon");
        filterChainDefinitionMap.put("/userInfo.data*","anon");
        filterChainDefinitionMap.put("/unauthorized.jsp","anon");
        filterChainDefinitionMap.put("/404.jsp","anon");
        filterChainDefinitionMap.put("/authenticated","authc");
        filterChainDefinitionMap.put("/login.jsp","authc");
        filterChainDefinitionMap.put("/imc.jsp","authc");

        filterChainDefinitionMap.put("/**", "kickout,userFilter,sysUser");//authc kickout,user,sysUser


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setFilters(map);
        return shiroFilterFactoryBean;
    }

    public Cookie getRememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(259200);
        return cookie;
    }

    public CookieRememberMeManager getRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager=new CookieRememberMeManager();
        byte[] bytes=Base64.decode("4AvVhmFLUs0KTA3Kprsdag==");
        cookieRememberMeManager.setCipherKey(bytes);
        cookieRememberMeManager.setCookie(getRememberMeCookie());
        return cookieRememberMeManager;
    }

    @Bean(name="ehCacheManagerBean")
    public EhCacheManager ehCacheManager() {
        EhCacheManager em =new EhCacheManager();
        //em.setCacheManagerConfigFile("classpath:ehcache3.xml");

        //将ehcacheManager转换成shiro包装后的ehcacheManager对象
        //em.setCacheManager(cacheManager);

        return em;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(ehCacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        securityManager.setRememberMeManager(getRememberMeManager());
        return securityManager;
    }

    @Bean
    public UserRealm myShiroRealm(){
        UserRealm myShiroRealm = new UserRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }


    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }



    public SessionIdGenerator getSessionIdGenerator(){
        return  new JavaUuidSessionIdGenerator();
    }

    public EnterpriseCacheSessionDAO getSessionDao(){
        EnterpriseCacheSessionDAO sessionDAO=new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        return  sessionDAO;
    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionDAO(getSessionDao());
        return sessionManager;
    }

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.addUrlPatterns("/*");
        return registration;
    }

}
