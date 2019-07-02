package myshiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/7/28.
 * IntelliJ IDEA 2017 of gzcss
 */
public class RetryLimitSimpleCredentialsMatcher extends SimpleCredentialsMatcher {
    private PasswordHelper passwordHelper;

    public void setPasswordHelper(PasswordHelper passwordHelper) {
        this.passwordHelper = passwordHelper;
    }

    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitSimpleCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    	 String username = (String)token.getPrincipal();
         //retry count + 1
         AtomicInteger retryCount = passwordRetryCache.get(username);
         if(retryCount == null) {
             retryCount = new AtomicInteger(0);
             passwordRetryCache.put(username, retryCount);
         }
         if(retryCount.incrementAndGet() > 5) {
             //if retry count > 5 throw
             throw new ExcessiveAttemptsException();
         }

         boolean matches = super.doCredentialsMatch(token, info);
         if(matches) {
             //clear retry count
             passwordRetryCache.remove(username);
         }
         return matches;
    }
}
