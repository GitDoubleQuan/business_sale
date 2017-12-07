package com.atguigu.route;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by Shuangquan.Xu on 2017/12/4.
 */
public class MyRouteDateSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceToken.getAndRemoveToken();
    }
}
