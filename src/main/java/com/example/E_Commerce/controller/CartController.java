package com.example.E_Commerce.controller;

import com.example.E_Commerce.modal.Cart;
import com.example.E_Commerce.modal.CartItems;
import com.example.E_Commerce.modal.User;
import com.example.E_Commerce.request.AddItemRequest;
import com.example.E_Commerce.service.CartItemService;
import com.example.E_Commerce.service.CartService;
import com.example.E_Commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Cart> findUserCartController(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItems> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) {

    }
}
