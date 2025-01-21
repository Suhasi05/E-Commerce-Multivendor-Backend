package com.example.E_Commerce.service;

import com.example.E_Commerce.domain.AccountStatus;
import com.example.E_Commerce.modal.Seller;

import java.util.List;

public interface SellerService {
    Seller getSellerProfileFromJwtToken(String token) throws Exception;
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id) throws Exception;
    Seller getSellerByEmail(String email) throws Exception;
    List<Seller> getAllSellers(AccountStatus status);
    Seller updateSeller(Long id, Seller seller) throws Exception;
    void deleteSeller(Long id) throws Exception;
    Seller verifyEmail(String email, String otp) throws Exception;
    Seller updateSellerAccountStatus(Long id, AccountStatus status) throws Exception;
}
