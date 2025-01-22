package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.Exception.SellerException;
import com.example.E_Commerce.Repository.AddressRepository;
import com.example.E_Commerce.Repository.SellerRepository;
import com.example.E_Commerce.config.JwtProvider;
import com.example.E_Commerce.domain.AccountStatus;
import com.example.E_Commerce.domain.USER_ROLE;
import com.example.E_Commerce.modal.Address;
import com.example.E_Commerce.modal.Seller;
import com.example.E_Commerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Seller getSellerProfileFromJwtToken(String token) throws Exception {
        String email = jwtProvider.getEmailFromToken(token);
        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
        if (sellerExist != null) {
            throw new Exception("Seller already exists, use diffrent email");
        }
        Address savedAddress = addressRepository.save(seller.getPickUpAddress());
        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setPickUpAddress(savedAddress);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setPhone(seller.getPhone());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        return sellerRepository.findById(id).orElseThrow(() -> new SellerException("Seller not found with id " + id));
    }

    @Override
    public Seller getSellerByEmail(String email) throws SellerException {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new SellerException("Seller not found");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws SellerException {
        Seller existingSeller = this.getSellerById(id);
        if (seller.getSellerName() != null) {
            existingSeller.setSellerName(seller.getSellerName());
        }
        if (seller.getPhone() != null) {
            existingSeller.setPhone(seller.getPhone());
        }
        if (seller.getEmail() != null) {
            existingSeller.setEmail(seller.getEmail());
        }
        if (seller.getBusinessDetails() != null && seller.getBusinessDetails().getBusinessName() != null) {
            existingSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        }
        if (seller.getBankDetails() != null
                && seller.getBankDetails().getAccountHolderName() != null
                && seller.getBankDetails().getIfscCode() != null
                && seller.getBankDetails().getAccountNumber() != null) {
            existingSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());
            existingSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
            existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());
        }
        if (seller.getPickUpAddress() != null
                && seller.getPickUpAddress().getAddress() != null
                && seller.getPickUpAddress().getPhone() != null
                && seller.getPickUpAddress().getCity() != null
                && seller.getPickUpAddress().getState() != null
        && seller.getPickUpAddress().getLocality() != null) {
            existingSeller.getPickUpAddress().setAddress(seller.getPickUpAddress().getAddress());
            existingSeller.getPickUpAddress().setPhone(seller.getPickUpAddress().getPhone());
            existingSeller.getPickUpAddress().setCity(seller.getPickUpAddress().getCity());
            existingSeller.getPickUpAddress().setState(seller.getPickUpAddress().getState());
            existingSeller.getPickUpAddress().setPinCode(seller.getPickUpAddress().getPinCode());
            existingSeller.getPickUpAddress().setLocality(seller.getPickUpAddress().getLocality());
        }
        if(seller.getGSTIN() != null){
            existingSeller.setGSTIN(seller.getGSTIN());
        }

        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws SellerException {
        Seller seller = this.getSellerById(id);
        sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws SellerException {
        Seller seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long id, AccountStatus status) throws SellerException {
        Seller seller = getSellerById(id);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}
