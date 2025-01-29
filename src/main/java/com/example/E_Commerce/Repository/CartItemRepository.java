package com.example.E_Commerce.Repository;

import com.example.E_Commerce.modal.Cart;
import com.example.E_Commerce.modal.CartItems;
import com.example.E_Commerce.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    CartItems findByCartAndProductAndSize(Cart cart, Product product, String size);
}
