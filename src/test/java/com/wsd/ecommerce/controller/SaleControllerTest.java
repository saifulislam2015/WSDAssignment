package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.model.Customer;
import com.wsd.ecommerce.model.Item;
import com.wsd.ecommerce.model.Sale;
import com.wsd.ecommerce.model.Wishlist;
import com.wsd.ecommerce.service.CustomerService;
import com.wsd.ecommerce.service.SaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
        mockMvc.perform(MockMvcRequestBuilders.get("/sales/today")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("250.0"));

    }
}
