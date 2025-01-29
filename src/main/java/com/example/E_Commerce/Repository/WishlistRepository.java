package com.example.E_Commerce.Repository;

import com.example.E_Commerce.modal.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Wishlist findByUserId(long userId);
}
