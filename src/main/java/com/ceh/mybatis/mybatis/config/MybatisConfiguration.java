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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by enHui.Chen on 2018/6/25.
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = MybatisConfiguration.BASEPACKAGE)
public class MybatisConfiguration {

    public static final String BASEPACKAGE = "com.ceh.mybatis.mybatis.mapper";

    @Autowired
    private CuxMybatisConfiguration cuxMybatisConfiguration;

    @Bean
    public MybatisPageHelper mybatisPageHelper() {
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
        if (StringUtils.hasText(cuxMybatisConfiguration.getMapperLocations()[0])) {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(cuxMybatisConfiguration.getMapperLocations()[0]));
        }

        // 不为Null,不为空字符串，且不为纯空格,若yml文件指定全局配置文件，其余配置失效
        if (StringUtils.hasText(cuxMybatisConfiguration.getConfigLocation())) {
            sqlSessionFactoryBean.setConfigLocation(resolver.getResource(cuxMybatisConfiguration.getConfigLocation()));
            return sqlSessionFactoryBean.getObject();
        }

        List<CuxMybatisConfiguration.MyInterceptor> myInterceptors = cuxMybatisConfiguration.getMyInterceptor();
        if (!CollectionUtils.isEmpty(myInterceptors)) {
            Interceptor[] interceptors = new Interceptor[myInterceptors.size()];
            for (int i = 0; i < myInterceptors.size(); i++) {
                interceptors[i] = (Interceptor) Class.forName(myInterceptors.get(i).getInterceptor()).newInstance();
                interceptors[i].setProperties(myInterceptors.get(i).getProperties());
            }
            sqlSessionFactoryBean.setPlugins(interceptors);
        }

        sqlSessionFactoryBean.setTypeAliasesPackage(cuxMybatisConfiguration.getTypeAliasesPackage());
        sqlSessionFactoryBean.setTypeHandlersPackage(cuxMybatisConfiguration.getTypeHandlersPackage());
        sqlSessionFactoryBean.setConfiguration(cuxMybatisConfiguration.getConfiguration());

        return sqlSessionFactoryBean.getObject();
    }
}
