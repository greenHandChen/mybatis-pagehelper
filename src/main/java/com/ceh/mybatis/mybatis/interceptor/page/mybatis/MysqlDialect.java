package com.ceh.mybatis.mybatis.interceptor.page.mybatis;

/**
 * Created by enHui.Chen on 2018/6/26.
 */
// mysql版本方言分页
public class MysqlDialect implements Dialect {
    private static final String LIMIT_SQL = "%s limit %s,%s";

    @Override
    public String getSqlByPage(String sql, int offset, int limit) {
        return String.format(LIMIT_SQL, sql, offset, limit);
    }

    @Override
    public String getSqlByCount(String sql) {
        return "select count(1) from (" + sql + ")";

    }
}
