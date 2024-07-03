package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.dto.MaxSaleDayDTO;
import com.wsd.ecommerce.dto.SaleItemDto;
import com.wsd.ecommerce.service.SaleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/sales/max")
    public ResponseEntity<Optional<MaxSaleDayDTO>> getMaxSalesDayWithinRange(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        Timestamp startDate = Timestamp.valueOf(fromDate.atStartOfDay());
        Timestamp endDate = Timestamp.valueOf(toDate.atStartOfDay().plusDays(1).minusNanos(1));

        return new ResponseEntity<>(saleService.getMaxSaleDayInRange(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/sales/top5SellingItems")
    public ResponseEntity<List<SaleItemDto>> getTop5SellingItems(){
        return new ResponseEntity<>(saleService.getTopSellingItemsOfAllTime(), HttpStatus.OK);
    }


}
