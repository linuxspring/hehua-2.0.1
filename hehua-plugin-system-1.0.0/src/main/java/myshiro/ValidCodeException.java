package myshiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by Administrator on 2017/7/28.
 * IntelliJ IDEA 2017 of gzcss
 */
public class ValidCodeException extends AuthenticationException {

    public ValidCodeException(String msg){
        super(msg);
    }
}