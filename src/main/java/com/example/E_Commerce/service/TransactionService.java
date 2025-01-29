package com.example.E_Commerce.service;

import com.example.E_Commerce.modal.Order;
import com.example.E_Commerce.modal.Seller;
import com.example.E_Commerce.modal.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Order order);
    List<Transaction> getTransactionsBySellerId(Seller seller);
    List<Transaction> getAllTransactions();
}
