package com.ceh.mybatis.mybatis.interceptor.page.mybatis;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Properties;

/**
 * Created by enHui.Chen on 2018/6/26.
 */
@Intercepts(@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
@Slf4j
public class MybatisPageHelper implements Interceptor {
    private Dialect dialect;
    private static final int MAPPED_STATMENT_INDEX = 0;
    private static final int OBJECT_INDEX = 1;
    private static final int ROWBOUNDS_INDEX = 2;
    private static final int RESULTHANDLER_INDEX = 3;
    private static final int ARGS_LENGTH = 4;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[MAPPED_STATMENT_INDEX];
        Object parameter = args[OBJECT_INDEX];// 查询参数
        RowBounds rowBounds = (RowBounds) args[ROWBOUNDS_INDEX];// 记录数
        ResultHandler resultHandler = (ResultHandler) args[RESULTHANDLER_INDEX];// 查询结果
        Executor executor = (Executor) invocation.getTarget();// 执行器
        BoundSql boundSql = null;
        CacheKey cacheKey = null;

        // 根据参数个数来生成缓存key
        if (args.length == ARGS_LENGTH) {
            boundSql = ms.getBoundSql(parameter);
            // 一级缓存的key: statementId+查询参数+记录数+查询的sql,通过哈希计算得出
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        }
        // 分页对象
        PageHelper pageHelper = getPageHelper(parameter);
        // 分页
        if (pageHelper != null) {
            // 封装sql
            String sql = getSql(boundSql.getSql(), pageHelper);
            // 封装boundSql
            BoundSql pageBoundSql = getBoundSql(ms, parameter, boundSql, sql);
            // 分页查询
            executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, pageBoundSql);
        }
        return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, boundSql);
    }

    private BoundSql getBoundSql(MappedStatement ms, Object parameter, BoundSql boundSql, String sql) {
        BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), parameter);
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (StringUtils.hasText(prop) && boundSql.hasAdditionalParameter(prop)) {
                pageBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return pageBoundSql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        try {
            String dialectStr = properties.getProperty("myDialect");
            if (StringUtils.hasText(dialectStr)) {
                dialect = (Dialect) Class.forName(dialectStr).newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PageHelper getPageHelper(Object parameters) {
        // isAssignableFrom()与instanceof的区别:
        // isAssignableFrom()用于判断某个类是否是属于某个类、接口、某个子类以及子接口
        // instanceof用于判断某个对象实例是否是属于某个类接口子类子接口。
        // 总结: isAssignableFrom()用于类的判断,instanceof用于对象实例的判断
        // 查询参数为空
        if (parameters == null) return null;
        // 单一查询参数为PageHelper
        if (parameters instanceof PageHelper) return (PageHelper) parameters;
        // 多个查询参数时为MapperMethod.ParamMap
        if (parameters instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap<Object> paramMap = (MapperMethod.ParamMap) parameters;
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                if (entry.getValue() instanceof PageHelper) {
                    return (PageHelper) entry.getValue();
                }
            }
        }
        return null;
    }

    public String getSql(String sql, PageHelper pageHelper) {
        String pageSql = sql.trim().replaceAll(";$", "");
        return dialect.getSqlByPage(pageSql, pageHelper.getPage() * pageHelper.getSize(), (pageHelper.getPage() + 1) * pageHelper.getSize());
    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
