package com.example.E_Commerce.service;

import com.example.E_Commerce.modal.User;

public interface UserService {
    User findUserByJwtToken(String jwtToken) throws Exception;
    User findUserByEmail(String email) throws Exception;

}
