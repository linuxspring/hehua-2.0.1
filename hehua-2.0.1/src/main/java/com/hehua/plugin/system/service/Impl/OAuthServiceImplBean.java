package com.hehua.plugin.system.service.Impl;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import util.SpringUtil;

/**
 * Created by Administrator on 2019/5/8.
 * IntelliJ IDEA 2019 of gzcss
 */
@Configuration
public class OAuthServiceImplBean {

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager em = new EhCacheManager();
        //将ehcacheManager转换成shiro包装后的ehcacheManager对象
        //em.setCacheManager(cacheManager);
        em.setCacheManagerConfigFile("classpath:ehcache2.xml");
        return em;
    }

    @Bean
    OAuthServiceImpl oAuthServiceImpl(){
        //CacheManager cacheManager= (CacheManager)SpringUtil.getBean("ehCacheManagerBean");
        CacheManager cacheManager=ehCacheManager();
        return new OAuthServiceImpl(cacheManager);
    }

}
