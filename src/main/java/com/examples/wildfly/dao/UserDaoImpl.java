package com.examples.wildfly.dao;

import com.examples.wildfly.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User findById(Integer id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAllUser() {
        return em.createNamedQuery("User.getAll", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }
}
