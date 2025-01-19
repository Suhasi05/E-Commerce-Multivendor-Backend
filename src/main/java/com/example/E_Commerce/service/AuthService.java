package com.example.E_Commerce.service;

import com.example.E_Commerce.response.SignupRequest;

public interface AuthService {
    String createUser(SignupRequest req);
}
