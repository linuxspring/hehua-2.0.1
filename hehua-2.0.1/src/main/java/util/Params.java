package util;

import java.lang.annotation.*;


/**
 * @author 作者 pengguojin:
 * @createTime 创建时间：2017年9月14日 上午9:27:19
 * @version 版本 1.0
 * @desc 描述
 * @project ITSM后台服务
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Params {
    /**参数列表*/
    Param[] value();
}

