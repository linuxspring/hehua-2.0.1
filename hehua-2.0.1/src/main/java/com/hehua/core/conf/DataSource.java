package com.hehua.core.conf;

/**
 * Created by Administrator on 2019/4/25.
 * IntelliJ IDEA 2019 of gzcss
 */

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    DataSourceEnum value() default DataSourceEnum.DB1;
}