package com.example.E_Commerce.service;

import com.example.E_Commerce.modal.CartItems;

public interface CartItemService {
    CartItems updateCartItem(Long userId, Long id, CartItems cartItems) throws Exception;
    void removeCartItem(Long userId, Long id) throws Exception;
    CartItems findCartItemById(Long id) throws Exception;

}
