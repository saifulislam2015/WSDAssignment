package com.wsd.ecommerce.service;

import com.wsd.ecommerce.dto.SaleCountDTO;
import com.wsd.ecommerce.dto.SaleItemDto;
import com.wsd.ecommerce.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleService saleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTotalSalesForToday() {
        LocalDate saleDate = LocalDate.now();
        LocalDateTime startOfDay = saleDate.atStartOfDay();
        LocalDateTime endOfDay = saleDate.atTime(LocalTime.MAX);
        Timestamp startOfDayTimestamp = Timestamp.valueOf(startOfDay);
        Timestamp endOfDayTimestamp = Timestamp.valueOf(endOfDay);

        Double expectedTotalSales = 1000.0;
        when(saleRepository.findTotalSalesByDate(startOfDayTimestamp, endOfDayTimestamp)).thenReturn(expectedTotalSales);

        Double totalSales = saleService.getTotalSalesForToday();
        assertEquals(expectedTotalSales, totalSales);
    }

    @Test
    public void testGetTopSellingItemsOfAllTime() {
        List<SaleItemDto> expectedItems = List.of(
                new SaleItemDto(1L, "Item1", 1000.0),
                new SaleItemDto(2L, "Item2", 900.0),
                new SaleItemDto(3L, "Item3", 800.0),
                new SaleItemDto(4L, "Item4", 700.0),
                new SaleItemDto(5L, "Item5", 600.0)
        );

        when(saleRepository.findTopSellingItemsOfAllTime(PageRequest.of(0, 5))).thenReturn(expectedItems);

        List<SaleItemDto> topItems = saleService.getTopSellingItemsOfAllTime();
        assertEquals(expectedItems, topItems);
    }

    @Test
    public void testGetTopSellingItemsBySalesCount() {
        List<SaleCountDTO> items = List.of(
                new SaleCountDTO(1L, "Item1", 1000.0, 2L),
                new SaleCountDTO(2L, "Item2", 900.0, 1L),
                new SaleCountDTO(3L, "Item3", 800.0, 1L),
                new SaleCountDTO(4L, "Item4", 700.0, 1L),
                new SaleCountDTO(5L, "Item5", 600.0, 1L)
        );
        Timestamp timestampOneMonthAgo = Timestamp.valueOf(LocalDateTime.now().minusMonths(1));
        when(saleRepository.findTopSellingItemsBySalesCountLastMonth(timestampOneMonthAgo)).thenReturn(items);

        List<SaleCountDTO> topItems = saleService.getTopSellingItemsBySalesCount();
        assertEquals(items, topItems);
    }

}

