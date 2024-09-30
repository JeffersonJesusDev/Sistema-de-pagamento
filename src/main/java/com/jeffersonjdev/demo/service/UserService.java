package com.jeffersonjdev.demo.service;

import com.jeffersonjdev.demo.dto.UserResponse;
import com.jeffersonjdev.demo.entity.User;
import com.jeffersonjdev.demo.repository.UserRepository;
import com.jeffersonjdev.demo.utils.RandomString;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService emailservice;

    public UserResponse registerUser(User user) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("Email already registered");
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);

            User savedUser = userRepository.save(user);

            UserResponse userResponse = new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getPassword());

            emailservice.sendVerificationEmail(user);


            return userResponse;
        }
    }

    public boolean verify(String verificationCode){

        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()){
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }
}
