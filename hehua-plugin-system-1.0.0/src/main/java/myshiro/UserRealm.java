package myshiro;


import com.hehua.plugin.system.service.Impl.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/7/28.
 * IntelliJ IDEA 2017 of gzcss
 */
public class UserRealm extends AuthorizingRealm {
    private static Logger logger = Logger.getLogger(UserRealm.class.getName());


//    public void setUserDAO(UserDAO userDAO) {
//        this.userDAO = userDAO;
//    }
//
//    private UserDAO userDAO;



    @Resource
    private UserServiceImpl userService;
    public UserServiceImpl getUserService() {
        return userService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username=(String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simple = new SimpleAuthorizationInfo();
        //simple.setRoles(this.userDAO.getRoles(username));
        //simple.setStringPermissions(this.userDAO.getPrimary(username));
        Set<String> roles=new HashSet();
        roles.add("admin");
        roles.add("user");
        simple.setRoles(roles);
        String res="OK";//userService.getList(1, 10);
        if ("OK".equals(res)) {
            System.out.println("res =GetRoles: " + res);
        }
        return simple;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //CustomUsernamePasswordToken customToken =(CustomUsernamePasswordToken)authenticationToken;
        UsernamePasswordToken customToken=(UsernamePasswordToken)authenticationToken;
       // String validCode = customToken.getValidCode();
        String username = customToken.getUsername();
        System.out.println("username = " + username);
        String res="OK";//userService.getList(1, 10);
        String pwd = userService.getPwdByAccount(username);
        //pwd="4280d89a5a03f812751f504cc10ee8a5";
        pwd=pwd.toLowerCase();
        System.out.println("pwd = " + pwd);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                username, //用户名
                pwd,//"4280d89a5a03f812751f504cc10ee8a5", //密码
                getName()  //realm name
        );

        return authenticationInfo;
    }



    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    protected void doClearCache(PrincipalCollection principals) {
        super.doClearCache(principals);
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }



    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
