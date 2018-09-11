package com.ceh.mybatis.mybatis.mapper;

import com.ceh.mybatis.mybatis.domain.User;
import com.ceh.mybatis.mybatis.interceptor.page.mybatis.PageHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by enHui.Chen on 2018/6/21.
 */
public interface UserDao {
    User getUserById(Integer id);

    List<User> getUserByUsername(@Param("username") String username,PageHelper pageHelper);

    Integer saveUser(User user);
}
