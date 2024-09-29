package com.jeffersonjdev.demo.service;

import com.jeffersonjdev.demo.entity.User;
import com.jeffersonjdev.demo.repository.UserRepository;
import com.jeffersonjdev.demo.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("Email already registered");
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);

            User savedUser = userRepository.save(user);
            return savedUser;
        }
    }
}
