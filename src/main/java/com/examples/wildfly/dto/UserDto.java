package com.examples.wildfly.dto;

import java.io.Serializable;

public class UserDto implements Serializable {

    private String name;
    private Integer age;

    public UserDto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
