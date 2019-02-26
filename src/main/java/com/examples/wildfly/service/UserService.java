package com.examples.wildfly.service;

import com.examples.wildfly.dto.UserDto;

import java.util.List;

public interface UserService {
    void add(UserDto userDto);
    List<UserDto> getAll();
}
