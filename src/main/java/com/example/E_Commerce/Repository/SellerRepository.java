package com.example.E_Commerce.Repository;

import com.example.E_Commerce.domain.AccountStatus;
import com.example.E_Commerce.modal.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus accountStatus);
}
