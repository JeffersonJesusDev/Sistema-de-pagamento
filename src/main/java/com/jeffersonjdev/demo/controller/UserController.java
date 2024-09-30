package com.jeffersonjdev.demo.controller;


import com.jeffersonjdev.demo.dto.UserRequest;
import com.jeffersonjdev.demo.dto.UserResponse;
import com.jeffersonjdev.demo.entity.User;
import com.jeffersonjdev.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) {
       User user = userRequest.toModel();
       UserResponse userSaved = userService.registerUser(user);
       return ResponseEntity.ok().body(userSaved);
    }
}
