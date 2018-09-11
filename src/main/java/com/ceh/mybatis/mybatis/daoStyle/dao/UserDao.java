package com.ceh.mybatis.mybatis.daoStyle.dao;

import com.ceh.mybatis.mybatis.domain.User;

import java.util.List;

/**
 * Created by enHui.Chen on 2018/6/21.
 */
public interface UserDao {
    User getUserById(Integer id);

    List<User> getUserByUsername(String username);

    Integer saveUser(User user);
}
