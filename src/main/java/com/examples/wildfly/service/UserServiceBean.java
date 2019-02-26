package com.examples.wildfly.service;

import com.examples.wildfly.dao.UserDao;
import com.examples.wildfly.dto.UserDto;
import com.examples.wildfly.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class UserServiceBean implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public void add(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());

        userDao.save(user);
        System.out.println("User Saved: "+user.getName() + " with Id: "+user.getId());
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userDao.getAllUser();
        return users.stream().map(toDto).collect(Collectors.toList());
    }

    private Function<User, UserDto> toDto = (user) -> new UserDto(user.getName(), user.getAge());
}
