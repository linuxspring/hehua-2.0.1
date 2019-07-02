package com.hehua.core.conf;

/**
 * Created by Administrator on 2019/4/25.
 * IntelliJ IDEA 2019 of gzcss
 */

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class MultipleDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
