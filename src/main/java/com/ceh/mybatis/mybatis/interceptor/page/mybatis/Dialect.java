package com.ceh.mybatis.mybatis.interceptor.page.mybatis;

/**
 * Created by enHui.Chen on 2018/6/26.
 */
public interface Dialect {
    /**
     * @Author: enHui.Chen
     * @Description: 分页
     * @Data 2018/6/26
     */
    String getSqlByPage(String sql, int offset, int limit);

     /**
       * @Author: enHui.Chen
       * @Description: 获取记录总数
       * @Data 2018/6/26
       */
    String getSqlByCount(String sql);

}
