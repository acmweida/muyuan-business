package com.muyuan.system.infrastructure.config.mybatis;

import com.muyuan.common.mybatis.jdbc.multi.JdbcConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.jdbc")
public class SystemJdbcConfig extends JdbcConfig {

    public static final String DATASOURCE_NAME = "system";
}