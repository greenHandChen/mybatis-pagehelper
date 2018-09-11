package com.ceh.mybatis.mybatis.daoStyle.dao;

import com.ceh.mybatis.mybatis.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by enHui.Chen on 2018/6/21.
 */
public class UserDaoImpl implements UserDao {
    private SqlSessionFactory sqlSessionFactory;

    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public User getUserById(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = null;
        try {
            user = sqlSession.selectOne("getUserById", id);
        } catch (Exception e) {

        } finally {
            sqlSession.close();
        }
        return user;
    }

    @Override
    public List<User> getUserByUsername(String username) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = null;
        try {
            users = sqlSession.selectList("getUserByUsername", username);
        } catch (Exception e) {

        } finally {
            sqlSession.close();
        }
        return users;
    }

    @Override
    public Integer saveUser(User user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Integer id = null;
        try {
            id = sqlSession.insert("saveUser", user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
        return id;
    }
}
