package com.ceh.mybatis.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by enHui.Chen on 2018/9/13.
 */
@Data
@ConfigurationProperties(prefix = CuxMybatisConfiguration.MYBATIS_PREFIX)
public class CuxMybatisConfiguration {

    public static final String MYBATIS_PREFIX = "cux.mybatis";

    private String[] mapperLocations;

    private String configLocation;

    private String typeAliasesPackage;

    private String typeHandlersPackage;

    private org.apache.ibatis.session.Configuration configuration;

    private final List<MyInterceptor> myInterceptor = new ArrayList();

    @Data
    public static class MyInterceptor {

        private String interceptor;

        private Properties properties;

    }

}
