package com.example.E_Commerce.service;

import com.example.E_Commerce.modal.Cart;
import com.example.E_Commerce.modal.CartItems;
import com.example.E_Commerce.modal.Product;
import com.example.E_Commerce.modal.User;

public interface CartService {
    CartItems addCartItem(User user, Product product, String size, int quantity);
    Cart findUserCart(User user);
}
