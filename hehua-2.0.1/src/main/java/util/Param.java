package util;

import java.lang.annotation.*;

/** 
* @author  作者 pengguojin: 
* @createTime 创建时间：2017年9月14日 上午9:27:45 
* @version 版本 1.0 
* @desc 描述
* @project ITSM后台服务 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Param {
    /**参数名称*/
    String name();
    
    /**参数中文名称**/
    String nameCN();
    
    /**参数描述信息*/
    String desc() default "";
    
    /**数据类型**/
    String type() default "String";
    
    /**判断是否是参数,默认是**/
    boolean parameter() default true;
    
    /**动态参数**/
    String[] dynamic() default "";
}
