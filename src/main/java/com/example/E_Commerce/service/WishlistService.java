package com.example.E_Commerce.service;

import com.example.E_Commerce.modal.Product;
import com.example.E_Commerce.modal.User;
import com.example.E_Commerce.modal.Wishlist;

public interface WishlistService {
    Wishlist createWishlist(User user);
    Wishlist getWishlistByUserId(User user);
    Wishlist addProductToWishlist(User user, Product product);
}
