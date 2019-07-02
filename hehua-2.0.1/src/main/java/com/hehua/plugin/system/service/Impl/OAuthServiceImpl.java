package com.hehua.plugin.system.service.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hehua.plugin.system.mapper.ClientMapper;
import com.hehua.plugin.system.model.Client;
import com.hehua.plugin.system.model.Plat_User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import util.PropertiesUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Administrator on 2017/9/12.
 * IntelliJ IDEA 2017 of gzcss
 */
//@Service
public class OAuthServiceImpl extends ServiceImpl<ClientMapper, Client> {


//    @Resource(name="sqlSessionFactoryBean")
//    private SqlSessionFactory sqlSessionFactory;

    private Cache cache;
    private Cache<String, AtomicInteger> passwordRetryCache;
    //Cache<String, AtomicInteger>

//    @Autowired
//    private ClientServiceImpl clientService;

    //@Autowired
    public OAuthServiceImpl(CacheManager cacheManager) {

        this.cache = cacheManager.getCache("code-cache");
        this.passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    public void addAuthCode(String authCode, String username) {
        cache.put(authCode, username);
    }

    public void addAccessToken(String accessToken, String username) {
        cache.put(accessToken, username);
    }

    public String getUsernameByAuthCode(String authCode) {
        return (String)cache.get(authCode);//.get();
    }

    public String getUsernameByAccessToken(String accessToken) {
        return (String)cache.get(accessToken);//.get();
    }

    public boolean checkAuthCode(String authCode) {
        return cache.get(authCode) != null;
    }

    public boolean checkAccessToken(String accessToken) {
        return cache.get(accessToken) != null;
    }

    public boolean checkClientId(String clientId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_id", clientId);

        //SqlSession sqlSession =sqlSessionFactory.openSession();
        //List<Client> client = sqlSession.selectList("com.gzcss.weixin.mapper.OAuthMapper.ListById",map);//this.baseMapper.selectByMap(map);
        //开启二级缓存，调用同一个mapper change by pgj
        Client oldClient =new Client();
        oldClient.setClientId(clientId);
        Client client = baseMapper.selectOne(oldClient);//.selectById(clientId);//.selectOne("com.gzcss.weixin.mapper.ClientMapper.getById",clientId);
        return client!=null;
        //return clientService.findByClientId(clientId) != null;
    }

    public boolean checkClientSecret(String clientSecret) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_secret", clientSecret);
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        EntityWrapper<Plat_User> ew = new EntityWrapper<Plat_User>();

        List<Client> client = this.sqlSessionBatch().selectList("com.hehua.plugin.system.mapper.OAuthMapper.ListBySecret",map);//this.baseMapper.selectByMap(map);
        return client.get(0)!=null;
        //return clientService.findByClientSecret(clientSecret) != null;
    }

    public int checkUserIsExits(String username,String userdot) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        map.put("userdot", userdot);
        map.put("cityCode", "031400");
        //map.put("cityCode", PropertiesUtil.getProperty("config.cityCode"));
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        EntityWrapper<Plat_User> ew = new EntityWrapper<Plat_User>();
        int total = this.sqlSessionBatch().selectOne("com.hehua.plugin.system.mapper.OAuthMapper.countUser",map);
        return total;
        //return clientService.findByClientSecret(clientSecret) != null;
    }

    public long getExpireIn() {
        return 3600L;
    }
    
    
    
   /////////////////
    public void doLimitMatch(String userTel) {
        AtomicInteger retryCount = passwordRetryCache.get(userTel);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(userTel, retryCount);
        }
        retryCount.incrementAndGet();
        passwordRetryCache.put(userTel,retryCount);
    }
 
    public boolean isLocked(String userTel){
        AtomicInteger num = passwordRetryCache.get(userTel);
        if(null != num && num.intValue() > 4){
        	System.out.println("登陆失败次数：" + num.intValue() + "次");
            return true;
        }
        return false;
    }
 
    public int getRetaryNum(String userTel){
        AtomicInteger num = passwordRetryCache.get(userTel);
        if(null == num){
            return 6;
        }
        return 6 - num.intValue();
    }
 
 
    public void removeRetary(String userTel){
        passwordRetryCache.remove(userTel);
    }
}
