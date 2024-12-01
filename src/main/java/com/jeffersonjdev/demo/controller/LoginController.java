package com.jeffersonjdev.demo.controller;

import com.jeffersonjdev.demo.dto.AuthenticationRequest;
import com.jeffersonjdev.demo.dto.AuthenticationResponse;
import com.jeffersonjdev.demo.entity.User;
import com.jeffersonjdev.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(), authenticationRequest.password()
        );
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken( (User) auth.getPrincipal());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
