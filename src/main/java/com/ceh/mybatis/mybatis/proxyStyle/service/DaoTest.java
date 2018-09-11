package com.ceh.mybatis.mybatis.proxyStyle.service;


import com.ceh.mybatis.mybatis.domain.User;
import com.ceh.mybatis.mybatis.interceptor.page.mybatis.PageHelper;
import com.ceh.mybatis.mybatis.mapper.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by enHui.Chen on 2018/6/21.
 */
public class DaoTest {
    private SqlSessionFactory sqlSessionFactory;
    private UserDao userDao;

    @Before
    public void init() throws IOException {
        // 创建sqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sfb = new SqlSessionFactoryBuilder();
        // 由sqlSessionFactoryBuilder和全局配置文件构建sqlSessionFactory
        this.sqlSessionFactory = sfb.build(Resources.getResourceAsStream("mybatis/sqlMapConfig.xml"));
        // mapper动态代理生成代理对象
        this.userDao = this.sqlSessionFactory.openSession().getMapper(UserDao.class);
    }

    @Test
    public void getByUserId() {
        System.out.println(userDao.getUserById(1));
    }

    @Test
    public void getUserByUsername() {
        System.out.println(userDao.getUserByUsername("e",new PageHelper()));
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setUsername("ceh");
        user.setPassword("ceh");
        System.out.println(userDao.saveUser(user));
    }
}
