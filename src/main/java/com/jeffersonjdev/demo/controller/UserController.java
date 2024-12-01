package com.jeffersonjdev.demo.controller;


import com.jeffersonjdev.demo.dto.AuthenticationRequest;
import com.jeffersonjdev.demo.dto.AuthenticationResponse;
import com.jeffersonjdev.demo.dto.UserRequest;
import com.jeffersonjdev.demo.dto.UserResponse;
import com.jeffersonjdev.demo.entity.User;
import com.jeffersonjdev.demo.service.TokenService;
import com.jeffersonjdev.demo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) throws MessagingException, UnsupportedEncodingException {
       User user = userRequest.toModel();
       UserResponse userSaved = userService.registerUser(user);
       return ResponseEntity.ok().body(userSaved);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code){
        if (userService.verify(code)){
            return "Verify_sucess";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("/teste")
    public String teste(){
        return "Você está logao";
    }

}
