package com.ceh.mybatis.mybatis.controller;

import com.ceh.mybatis.mybatis.domain.User;
import com.ceh.mybatis.mybatis.interceptor.page.mybatis.PageHelper;
import com.ceh.mybatis.mybatis.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by enHui.Chen on 2018/9/11.
 */
@RestController
@RequestMapping("/api")
public class MybatisController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/getUserByUsername", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserByUsername(@RequestParam(value = "username", required = false) String username) {
        return new ResponseEntity(userDao.getUserByUsername(username, new PageHelper()), HttpStatus.OK);
    }
}
