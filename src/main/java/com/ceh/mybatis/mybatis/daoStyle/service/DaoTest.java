package com.ceh.mybatis.mybatis.daoStyle.service;


import com.ceh.mybatis.mybatis.daoStyle.dao.UserDaoImpl;
import com.ceh.mybatis.mybatis.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * Created by enHui.Chen on 2018/6/21.
 */
public class DaoTest {
    private SqlSessionFactory sqlSessionFactory;
    private UserDaoImpl userDao;

    //    @Before
    public void init() throws IOException {
        // 创建sqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sfb = new SqlSessionFactoryBuilder();
        // 由sqlSessionFactoryBuilder和全局配置文件构建sqlSessionFactory
        this.sqlSessionFactory = sfb.build(Resources.getResourceAsStream("mybatis/sqlMapConfig.xml"));
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setUsername("ceh");
        user.setPassword("ceh");
        System.out.println(userDao.saveUser(user));
    }

    @Test
    public void getByUserId() {
        System.out.println(userDao.getUserById(1));
    }

    @Test
    public void getUserByUsername() {
        System.out.println(userDao.getUserByUsername("e"));
    }

    @Test
    public void test() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("C:\\Users\\Administrator\\Desktop\\a.txt")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\b.txt")));
        String str = "";
        int count = 0;
        while ((str = bufferedReader.readLine()) != null) {
            bufferedWriter.write("\"" + str + "\",");
            bufferedWriter.newLine();
            System.out.println(str);
            count++;
        }
        System.out.println(count);
        bufferedWriter.close();
        bufferedReader.close();
    }
}
