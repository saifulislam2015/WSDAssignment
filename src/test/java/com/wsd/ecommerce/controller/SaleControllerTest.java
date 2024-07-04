package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.dto.MaxSaleDayDTO;
import com.wsd.ecommerce.dto.SaleCountDTO;
import com.wsd.ecommerce.dto.SaleItemDto;
import com.wsd.ecommerce.model.Item;
import com.wsd.ecommerce.model.Sale;
import com.wsd.ecommerce.service.SaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SaleController.class)
public class SaleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaleService saleService;

    @Test
    public void testGetTotalSalesForToday() throws Exception {

        // Mock data
        Item mockItem1 = new Item(1L,"Item 1", 200);
        Item mockItem2 = new Item(2L,"Item 2", 100);
        Sale mockSale1 = new Sale(1L, 100, new Timestamp(new Date().getTime()), mockItem1);
        Sale mockSale2 = new Sale(1L, 150, new Timestamp(new Date().getTime()), mockItem2);

        // Mock service method
        when(saleService.getTotalSalesForToday()).thenReturn(250.0);

        // Perform GET request and validate
        mockMvc.perform(get("/sales/today")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("250.0"));

    }

    @Test
    public void testGetMaxSalesDayWithinRange() throws Exception {

        // Mock data
        java.sql.Date saleDate = java.sql.Date.valueOf(LocalDate.of(2024, 7, 2));
        Double totalAmount = 500.0;
        MaxSaleDayDTO maxSaleDayDTO = new MaxSaleDayDTO(saleDate, totalAmount);

        Timestamp startDate = Timestamp.valueOf(LocalDate.of(2024, 7, 1).atStartOfDay());
        Timestamp endDate = Timestamp.valueOf(LocalDate.of(2024, 7, 3).atStartOfDay().plusDays(1).minusNanos(1));

        Mockito.when(saleService.getMaxSaleDayInRange(startDate, endDate)).thenReturn(Optional.of(maxSaleDayDTO));

        mockMvc.perform(get("/sales/max")
                        .param("fromDate", "2024-07-01")
                        .param("toDate", "2024-07-03")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.saleDate").value(saleDate.toString()))
                .andExpect(jsonPath("$.totalAmount").value(totalAmount));

    }

    @Test
    public void testGetTop5SellingItems() throws Exception {
        // Arrange: Mocking 10 Sale and Item entities
        Item item1 = new Item(1L, "Item1", 100);
        Item item2 = new Item(2L, "Item2", 110);
        Item item3 = new Item(3L, "Item3", 120);
        Item item4 = new Item(4L, "Item4", 130);
        Item item5 = new Item(5L, "Item5", 140);
        Item item6 = new Item(6L, "Item6", 150);
        Item item7 = new Item(7L, "Item7", 160);
        Item item8 = new Item(8L, "Item8", 170);
        Item item9 = new Item(9L, "Item9", 180);
        Item item10 = new Item(10L, "Item10", 190);

        Sale sale1 = new Sale(1L, 1000.0, Timestamp.valueOf("2024-07-01 12:00:00"), item1);
        Sale sale2 = new Sale(2L, 900.0, Timestamp.valueOf("2024-07-02 12:00:00"), item2);
        Sale sale3 = new Sale(3L, 800.0, Timestamp.valueOf("2024-07-03 12:00:00"), item3);
        Sale sale4 = new Sale(4L, 700.0, Timestamp.valueOf("2024-07-04 12:00:00"), item4);
        Sale sale5 = new Sale(5L, 600.0, Timestamp.valueOf("2024-07-05 12:00:00"), item5);
        Sale sale6 = new Sale(6L, 500.0, Timestamp.valueOf("2024-07-06 12:00:00"), item6);
        Sale sale7 = new Sale(7L, 400.0, Timestamp.valueOf("2024-07-07 12:00:00"), item7);
        Sale sale8 = new Sale(8L, 300.0, Timestamp.valueOf("2024-07-08 12:00:00"), item8);
        Sale sale9 = new Sale(9L, 200.0, Timestamp.valueOf("2024-07-09 12:00:00"), item9);
        Sale sale10 = new Sale(10L, 100.0, Timestamp.valueOf("2024-07-10 12:00:00"), item10);

        List<Sale> sales = Arrays.asList(sale1, sale2, sale3, sale4, sale5, sale6, sale7, sale8, sale9, sale10);

        // Mocking the SaleService to return the top 5 sales
        List<SaleItemDto> expectedItems = Arrays.asList(
                new SaleItemDto(1L, "Item1", 1000.0),
                new SaleItemDto(2L, "Item2", 900.0),
                new SaleItemDto(3L, "Item3", 800.0),
                new SaleItemDto(4L, "Item4", 700.0),
                new SaleItemDto(5L, "Item5", 600.0)
        );

        Mockito.when(saleService.getTopSellingItemsOfAllTime()).thenReturn(expectedItems);

        // Act and Assert
        mockMvc.perform(get("/sales/top5SellingItems")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    public void testGetTop5SellingItemsBySalesCount() throws Exception {
        // Arrange: Mocking 10 Sale and Item entities
        Item item1 = new Item(1L, "Item1", 100);
        Item item2 = new Item(2L, "Item2", 110);
        Item item3 = new Item(3L, "Item3", 120);
        Item item4 = new Item(4L, "Item4", 130);
        Item item5 = new Item(5L, "Item5", 140);
        Item item6 = new Item(6L, "Item6", 150);
        Item item7 = new Item(7L, "Item7", 160);
        Item item8 = new Item(8L, "Item8", 170);
        Item item9 = new Item(9L, "Item9", 180);
        Item item10 = new Item(10L, "Item10", 190);

        Sale sale1 = new Sale(1L, 1000.0, Timestamp.valueOf("2024-07-01 12:00:00"), item1);
        Sale sale2 = new Sale(2L, 900.0, Timestamp.valueOf("2024-07-02 12:00:00"), item2);
        Sale sale3 = new Sale(3L, 800.0, Timestamp.valueOf("2024-07-03 12:00:00"), item3);
        Sale sale4 = new Sale(4L, 700.0, Timestamp.valueOf("2024-07-04 12:00:00"), item4);
        Sale sale5 = new Sale(5L, 600.0, Timestamp.valueOf("2024-07-05 12:00:00"), item5);
        Sale sale6 = new Sale(6L, 500.0, Timestamp.valueOf("2024-07-06 12:00:00"), item6);
        Sale sale7 = new Sale(7L, 400.0, Timestamp.valueOf("2024-07-07 12:00:00"), item7);
        Sale sale8 = new Sale(8L, 300.0, Timestamp.valueOf("2024-07-08 12:00:00"), item8);
        Sale sale9 = new Sale(9L, 200.0, Timestamp.valueOf("2024-07-09 12:00:00"), item9);
        Sale sale10 = new Sale(10L, 100.0, Timestamp.valueOf("2024-07-10 12:00:00"), item10);
        Sale sale11 = new Sale(10L, 100.0, Timestamp.valueOf("2024-07-10 12:00:00"), item10);

        // Mocking the SaleService to return the top 5 sales
        List<SaleCountDTO> expectedItems = Arrays.asList(
                new SaleCountDTO(10L, "Item10", 200.0, 2L),
                new SaleCountDTO(1L, "Item1", 1000.0, 1L),
                new SaleCountDTO(2L, "Item2", 900.0, 1L),
                new SaleCountDTO(3L, "Item3", 800.0, 1L),
                new SaleCountDTO(4L, "Item4", 700.0, 1L)
        );

        Mockito.when(saleService.getTopSellingItemsBySalesCount()).thenReturn(expectedItems);

        // Act and Assert
        mockMvc.perform(get("/sales/top5SellingItemsBySalesCount")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }
}
