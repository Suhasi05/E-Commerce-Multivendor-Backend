package com.example.E_Commerce.controller;

import com.example.E_Commerce.Exception.ProductException;
import com.example.E_Commerce.Repository.UserRepository;
import com.example.E_Commerce.Repository.WishlistRepository;
import com.example.E_Commerce.modal.Product;
import com.example.E_Commerce.modal.User;
import com.example.E_Commerce.modal.Wishlist;
import com.example.E_Commerce.service.ProductService;
import com.example.E_Commerce.service.UserService;
import com.example.E_Commerce.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Wishlist> getWishlistByUserId(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Wishlist wishlist = wishlistService.getWishlistByUserId(user);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlsit(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwt);
        Wishlist updateWishlist = wishlistService.addProductToWishlist(user, product);
        return ResponseEntity.ok(updateWishlist);
    }
}
