package com.wsd.ecommerce.service;

import com.wsd.ecommerce.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Double getTotalSalesForToday() {
        LocalDate saleDate = LocalDate.now(); // Replace with your desired date
        LocalDateTime startOfDay = saleDate.atStartOfDay();
        LocalDateTime endOfDay = saleDate.atTime(LocalTime.MAX);

        Timestamp startOfDayTimestamp = Timestamp.valueOf(startOfDay);
        Timestamp endOfDayTimestamp = Timestamp.valueOf(endOfDay);

        return saleRepository.findTotalSalesByDate(startOfDayTimestamp, endOfDayTimestamp);
    }

    public List<Object[]> getMaxSaleDayInRange(LocalDate startDate, LocalDate endDate) {
        return saleRepository.findMaxSaleDayInRange(startDate, endDate);
    }

//    public List<Object[]> getTopSellingItemsOfAllTime() {
//        return saleRepository.findTopSellingItemsOfAllTime();
//    }
//
//    public List<Object[]> getTopSellingItemsInLastMonth(LocalDate startDate, LocalDate endDate) {
//        return saleRepository.findTopSellingItemsInLastMonth(startDate, endDate);
//    }
}

