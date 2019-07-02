package com.hehua.core.conf;

/**
 * Created by Administrator on 2019/4/25.
 * IntelliJ IDEA 2019 of gzcss
 */
public enum DataSourceEnum {

    DB1("db1"),DB2("db2");

    private String value;

    DataSourceEnum(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}