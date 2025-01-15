package com.example.E_Commerce.controller;

import com.example.E_Commerce.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/health-check")
    public ApiResponse HealthCheck() {
        ApiResponse response = new ApiResponse();
        response.setMessage("OK");
        return response;
    }
}
