package com.example.E_Commerce.controller;

import com.example.E_Commerce.modal.Cart;
import com.example.E_Commerce.modal.CartItems;
import com.example.E_Commerce.modal.Product;
import com.example.E_Commerce.modal.User;
import com.example.E_Commerce.request.AddItemRequest;
import com.example.E_Commerce.response.ApiResponse;
import com.example.E_Commerce.service.CartItemService;
import com.example.E_Commerce.service.CartService;
import com.example.E_Commerce.service.ProductService;
import com.example.E_Commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Cart> findUserCartController(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItems> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Product product = productService.findProductById(req.getProductId());

        CartItems items = cartService.addCartItem(user, product, req.getSize(), req.getQuantity());

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart sucessfully");

        return new ResponseEntity<>(items, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item removed from cart sucessfully");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItems> updateItemHandler(
            @PathVariable Long cartItemId,
            @RequestBody CartItems cartItems,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        CartItems updateCartItem = null;
        if(cartItems.getQuantity() > 0) {
            updateCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItems);
        }
        return new ResponseEntity<>(updateCartItem, HttpStatus.ACCEPTED);
    }

}
