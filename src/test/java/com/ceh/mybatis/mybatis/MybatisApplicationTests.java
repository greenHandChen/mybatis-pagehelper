package com.ceh.mybatis.mybatis;

import com.ceh.mybatis.mybatis.interceptor.page.mybatis.PageHelper;
import com.ceh.mybatis.mybatis.mapper.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisApplicationTests {
	@Autowired
	UserDao userDao;

	@Test
	public void contextLoads() {
//		System.out.println(userDao.getUserById(1));
		System.out.println(userDao.getUserByUsername("c",new PageHelper(0,3)));
//		User user = new User();
//		user.setPassword("qwerC");
//		user.setUsername("qwerC");
//		System.out.println(userDao.saveUser(user));
	}

}
