package com.example.E_Commerce.Repository;

import com.example.E_Commerce.modal.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySeller_Id(Long id);

}
