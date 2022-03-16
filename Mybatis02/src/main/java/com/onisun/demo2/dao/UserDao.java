package com.onisun.demo2.dao;

import com.onisun.demo2.bean.User;

import java.util.List;

/**
 * @author Neo
 * @version 1.0
 */
public interface UserDao {

    Integer save(User user);

    List<User> queryAll();
}
