package com.example.E_Commerce.Repository;

import com.example.E_Commerce.modal.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByEmail(String email);

    VerificationCode findByOtp(String otp);
}
