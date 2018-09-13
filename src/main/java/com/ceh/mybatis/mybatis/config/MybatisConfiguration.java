package com.ceh.mybatis.mybatis.config;

import com.ceh.mybatis.mybatis.interceptor.page.mybatis.MybatisPageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by enHui.Chen on 2018/6/25.
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.ceh.mybatis.mybatis.mapper")
public class MybatisConfiguration {
    @Autowired
    private MybatisProperties properties;

    @Bean
    public MybatisPageHelper mybatisPageHelper(){
        return new MybatisPageHelper();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@SuppressWarnings("all") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MybatisPageHelper mybatisPageHelper = mybatisPageHelper();
        // 配置springboot对mybatis的bean扫描的类
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 不为Null,不为空字符串，且不为纯空格
        if (StringUtils.hasText(properties.getMapperLocations()[0])) {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(properties.getMapperLocations()[0]));
        }

        // 不为Null,不为空字符串，且不为纯空格,若yml文件指定全局配置文件，其余配置失效
        if (StringUtils.hasText(properties.getConfigLocation())) {
            sqlSessionFactoryBean.setConfigLocation(resolver.getResource(properties.getConfigLocation()));
            return sqlSessionFactoryBean.getObject();
        }

        sqlSessionFactoryBean.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        sqlSessionFactoryBean.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        sqlSessionFactoryBean.setConfiguration(properties.getConfiguration());
        // TODO:插件问题待解决 start
        Properties properties = new Properties();
        properties.setProperty("myDialect", "com.ceh.mybatis.mybatis.interceptor.page.mybatis.MysqlDialect");
        mybatisPageHelper.setProperties(properties);
        Interceptor[] interceptors = new Interceptor[1];
        interceptors[0] = mybatisPageHelper;
        sqlSessionFactoryBean.setPlugins(interceptors);
        // TODO:插件问题待解决 end
        return sqlSessionFactoryBean.getObject();
    }
}
