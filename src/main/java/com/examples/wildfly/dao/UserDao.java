package com.examples.wildfly.dao;

import com.examples.wildfly.model.User;

import java.util.List;

public interface UserDao {
    User findById(Integer id);
    List<User> getAllUser();
    void save(User user);
}
