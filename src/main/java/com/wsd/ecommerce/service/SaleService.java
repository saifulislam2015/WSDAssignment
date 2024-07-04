package com.wsd.ecommerce.service;

import com.wsd.ecommerce.dto.MaxSaleDayDTO;
import com.wsd.ecommerce.dto.SaleCountDTO;
import com.wsd.ecommerce.dto.SaleItemDto;
import com.wsd.ecommerce.repository.SaleRepository;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Double getTotalSalesForToday() {
        LocalDate saleDate = LocalDate.now();
        LocalDateTime startOfDay = saleDate.atStartOfDay();
        LocalDateTime endOfDay = saleDate.atTime(LocalTime.MAX);

        Timestamp startOfDayTimestamp = Timestamp.valueOf(startOfDay);
        Timestamp endOfDayTimestamp = Timestamp.valueOf(endOfDay);

        return saleRepository.findTotalSalesByDate(startOfDayTimestamp, endOfDayTimestamp);
    }

    public Optional<MaxSaleDayDTO> getMaxSaleDayInRange(Timestamp startDate, Timestamp endDate) {
        List<Tuple> results = saleRepository.findMaxSaleDayInRange(startDate, endDate);

        if (results.isEmpty()) {
            return Optional.empty();
        }

        Tuple result = results.getFirst();
        Date saleDate1 = result.get("saleDate", Date.class);
        Double totalAmount = result.get("totalAmount", Double.class);

        MaxSaleDayDTO dto = new MaxSaleDayDTO(saleDate1, totalAmount);
        return Optional.of(dto);
    }

    public List<SaleItemDto> getTopSellingItemsOfAllTime() {
        Pageable pageable = PageRequest.of(0, 5);
        return saleRepository.findTopSellingItemsOfAllTime(pageable);
    }

    public List<SaleCountDTO> getTopSellingItemsBySalesCount() {
        Timestamp timestampOneMonthAgo = Timestamp.valueOf(LocalDateTime.now().minusMonths(1));
        return saleRepository.findTopSellingItemsBySalesCountLastMonth(timestampOneMonthAgo);
    }
}

