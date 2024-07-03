package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/sales/today")
    public ResponseEntity<Double> getTotalSalesForToday() {
        return new ResponseEntity<>(saleService.getTotalSalesForToday(), HttpStatus.OK);
    }
}
