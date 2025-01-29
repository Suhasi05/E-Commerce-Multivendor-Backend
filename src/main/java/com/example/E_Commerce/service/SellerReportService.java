package com.example.E_Commerce.service;

import com.example.E_Commerce.modal.Seller;
import com.example.E_Commerce.modal.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
