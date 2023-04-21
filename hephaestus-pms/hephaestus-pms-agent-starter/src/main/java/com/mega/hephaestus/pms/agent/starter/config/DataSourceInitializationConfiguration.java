package com.mega.hephaestus.pms.agent.starter.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.ds.ItemDataSource;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/14 21:54
 */
@Configuration
public class DataSourceInitializationConfiguration {

//    @Resource
//    private javax.sql.DataSource runtimeDataSource;

    @Resource
    private DynamicRoutingDataSource dataSource;

    @Bean
    SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(SqlInitializationProperties properties) throws SQLException {
        DataSource runtime = dataSource.getDataSource("runtime").unwrap(DruidDataSource.class);
        return new SqlDataSourceScriptDatabaseInitializer(
                determineDataSource(runtime, properties.getUsername(), properties.getPassword()), properties);
    }

    private static DataSource determineDataSource(DataSource dataSource, String username, String password) {
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            return DataSourceBuilder.derivedFrom(dataSource).username(username).password(password)
                    .type(SimpleDriverDataSource.class).build();
        }
        return dataSource;
    }


}
