package com.techzealot.spring.playground;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 测试连接池初始化专用
 */
@Configuration
public class PoolConfig {
    /**
     * 采用HikariDataSource(HikariConfig)构造器会触发连接有效性检查,无参构造器为延迟加载
     *
     * @param properties
     * @return
     */
    //@Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    HikariDataSource dataSource(DataSourceProperties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getUrl());
        config.setUsername(properties.getUsername());
        config.setPassword(properties.getPassword());
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
}
