package com.example.E_Commerce.Repository;

import com.example.E_Commerce.modal.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
}
