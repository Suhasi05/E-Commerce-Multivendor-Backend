package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.Repository.CartItemRepository;
import com.example.E_Commerce.modal.CartItems;
import com.example.E_Commerce.modal.User;
import com.example.E_Commerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItems updateCartItem(Long userId, Long id, CartItems cartItems) throws Exception {
        CartItems item = findCartItemById(id);
        User cartItemUser = item.getCart().getUser();
        if(cartItemUser.getId().equals(userId)) {
            item.setQuantity(cartItems.getQuantity());
            item.setMRPPrice(item.getQuantity()*item.getProduct().getMRPPrice());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellerPrice());
            return cartItemRepository.save(item);
        }
        throw new Exception("You can't update this cartItem");
    }

    @Override
    public void removeCartItem(Long userId, Long id) throws Exception {
        CartItems item = findCartItemById(id);
        User cartItemUser = item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)) {
            cartItemRepository.delete(item);
        } else {
            throw new Exception("You can't delete this item");
        }
    }

    @Override
    public CartItems findCartItemById(Long id) throws Exception {
        return cartItemRepository.findById(id).orElseThrow(() -> new Exception("Cart item not found with id " + id));
    }
}
