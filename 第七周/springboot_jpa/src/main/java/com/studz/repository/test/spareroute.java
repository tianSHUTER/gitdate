package com.studz.repository.test;

import com.studz.domain.User;
import com.studz.repository.DataSourceConfig;
import com.studz.repository.TargetDateSource;

public class spareroute {

    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public User findById(String id) {
        return new User();

    }

}
