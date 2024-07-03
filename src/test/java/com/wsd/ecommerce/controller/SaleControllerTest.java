package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.dto.MaxSaleDayDTO;
import com.wsd.ecommerce.model.Customer;
import com.wsd.ecommerce.model.Item;
import com.wsd.ecommerce.model.Sale;
import com.wsd.ecommerce.model.Wishlist;
import com.wsd.ecommerce.service.CustomerService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;

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
}
