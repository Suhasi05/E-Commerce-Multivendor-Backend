package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.Repository.CartItemRepository;
import com.example.E_Commerce.Repository.CartRepository;
import com.example.E_Commerce.modal.Cart;
import com.example.E_Commerce.modal.CartItems;
import com.example.E_Commerce.modal.Product;
import com.example.E_Commerce.modal.User;
import com.example.E_Commerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItems addCartItem(User user, Product product, String size, int quantity) {
        Cart cart = findUserCart(user);

        CartItems isPresent = cartItemRepository.findByCartAndProductAndSize(cart, product, size);
        if(isPresent == null) {
            CartItems cartItems = new CartItems();
            cartItems.setProduct(product);
            cartItems.setQuantity(quantity);
            cartItems.setUserId(user.getId());
            cartItems.setSize(size);

            int totalPrice = quantity * product.getSellerPrice();
            cartItems.setSellingPrice(totalPrice);
            cartItems.setMRPPrice(quantity * product.getSellerPrice());

            cart.getCartItems().add(cartItems);
            cartItems.setCart(cart);

            return cartItemRepository.save(cartItems);
        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());

        int totalPrice = 0;
        int totalDiscountPrice = 0;
        int totalItem = 0;

        for(CartItems cartItem : cart.getCartItems()) {
            totalItem += cartItem.getQuantity();
            totalPrice += cartItem.getMRPPrice();
            totalDiscountPrice += cartItem.getSellingPrice();
        }

        cart.setTotalMRPPrice(totalPrice);
        cart.setTotalSellingPrice(totalDiscountPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(calculateDiscountPercentage(totalPrice, totalDiscountPrice));

        return cart;
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if(mrpPrice <= 0) {
            System.out.println("MRP -----------------------> "+mrpPrice);
//            throw new IllegalArgumentException("Actual price must be greater than 0");
            return 0;
        }
        if(sellingPrice <= 0) {
            System.out.println("-----------------------> "+sellingPrice);
            throw new IllegalArgumentException("Actual price must be greater than 0");
        }
        double discountPercentage = ((double)(mrpPrice - sellingPrice) / mrpPrice) * 100;
        return (int) Math.round(discountPercentage);
    }

}
