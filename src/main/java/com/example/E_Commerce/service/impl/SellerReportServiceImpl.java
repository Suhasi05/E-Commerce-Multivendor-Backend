package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.Repository.SellerReportRepository;
import com.example.E_Commerce.modal.Seller;
import com.example.E_Commerce.modal.SellerReport;
import com.example.E_Commerce.service.SellerReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {

    private final SellerReportRepository sellerReportRepository;

    @Override
    public SellerReport getSellerReport(Seller seller) {
        SellerReport sr = sellerReportRepository.findBySellerId(seller.getId());
        if(sr == null) {
            SellerReport sr1 = new SellerReport();
            sr1.setSeller(seller);
            return sellerReportRepository.findBySellerId(seller.getId());
        }
        return sr;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return sellerReportRepository.save(sellerReport);
    }
}
