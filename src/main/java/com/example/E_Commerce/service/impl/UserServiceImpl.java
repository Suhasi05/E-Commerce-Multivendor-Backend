package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.Repository.UserRepository;
import com.example.E_Commerce.config.JwtProvider;
import com.example.E_Commerce.modal.User;
import com.example.E_Commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwtToken) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwtToken);
        User user = this.findUserByEmail(email);
        if(user == null) {
            throw new Exception("User not found with email - "+email);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = this.userRepository.findByEmail(email);
        if(user == null) {
            throw new Exception("User not found with email - " + email);
        }
        return user;
    }
}
