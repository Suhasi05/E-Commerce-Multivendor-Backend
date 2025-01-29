package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.Repository.CartRepository;
import com.example.E_Commerce.Repository.CouponRepository;
import com.example.E_Commerce.Repository.UserRepository;
import com.example.E_Commerce.modal.Cart;
import com.example.E_Commerce.modal.Coupon;
import com.example.E_Commerce.modal.User;
import com.example.E_Commerce.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Override
    public Cart applyCoupon(String code, double orderValue, User user) throws Exception {
        Coupon coupon = couponRepository.findByCode(code);
        Cart cart = cartRepository.findByUserId(user.getId());
        if(coupon == null) {
            throw new Exception("Coupon not valid");
        }
        if(user.getUsedCoupons().contains(coupon)) {
            throw new Exception("Coupon is already used");
        }
        if(orderValue <= coupon.getMinimumOrderValue()) {
            throw new Exception("Order value must be greater than the minimum order value" + coupon.getMinimumOrderValue());
        }
        if(coupon.isActive() && LocalDate.now().isAfter(coupon.getValidityStartDate()) && LocalDate.now().isBefore(coupon.getValidityEndDate())) {
            user.getUsedCoupons().add(coupon);
            userRepository.save(user);

            double discountedPrice = cart.getTotalSellingPrice()*coupon.getDiscountPercentage()/100;

            cart.setTotalSellingPrice(cart.getTotalSellingPrice()-discountedPrice);
            cart.setCouponCode(code);
            cartRepository.save(cart);
            return cart;
        }
        throw new Exception("Coupon not valid");
    }

    @Override
    public Cart removeCoupon(String code, User user) throws Exception {
        Coupon coupon = couponRepository.findByCode(code);
        if(coupon == null) {
            throw new Exception("Coupon not found");
        }

        Cart cart = cartRepository.findByUserId(user.getId());
        double discountedPrice = cart.getTotalSellingPrice()*coupon.getDiscountPercentage()/100;
        cart.setTotalSellingPrice(cart.getTotalSellingPrice()+discountedPrice);
        cart.setCouponCode(null);

        return cartRepository.save(cart);
    }

    @Override
    public Coupon findCouponById(Long id) throws Exception {
        return couponRepository.findById(id).orElseThrow(() -> new Exception("Coupon not found"));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCoupon(Long id) throws Exception {
        findCouponById(id);
        couponRepository.deleteById(id);
    }
}
