package com.jeffersonjdev.demo.dto;

import com.jeffersonjdev.demo.entity.User;

public record UserRequest(String name, String email, String password) {

    public User toModel(){
        return new User(name, email, password);
    }
}
