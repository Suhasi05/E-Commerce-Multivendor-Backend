package com.example.E_Commerce.service;

import com.example.E_Commerce.domain.USER_ROLE;
import com.example.E_Commerce.request.LoginRequest;
import com.example.E_Commerce.response.AuthResponse;
import com.example.E_Commerce.response.SignupRequest;

public interface AuthService {
    String createUser(SignupRequest req);
    void sentLoginOtp(String email, USER_ROLE role);
    AuthResponse signin(LoginRequest req) throws Exception;
}
