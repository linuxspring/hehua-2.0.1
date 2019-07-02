package com.hehua.core.config;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;

/**
 * Created by Administrator on 2019/4/29.
 * IntelliJ IDEA 2019 of gzcss
 */
//@CacheConfig(cacheNames = {"myCache"})
public class MyEhCacheManager extends EhCacheManager {

    @Value("${spring.ehcache.config}")
    private String file;

    @Override
    public String getCacheManagerConfigFile() {
        return super.getCacheManagerConfigFile();
    }

    @Override
    public void setCacheManagerConfigFile(String classpathLocation) {

        this.setCacheManagerConfigFile(file);
        super.setCacheManagerConfigFile(classpathLocation);
    }
}
