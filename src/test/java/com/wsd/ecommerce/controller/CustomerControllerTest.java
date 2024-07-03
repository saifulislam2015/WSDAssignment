package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.model.Customer;
import com.wsd.ecommerce.model.Item;
import com.wsd.ecommerce.model.Wishlist;
import com.wsd.ecommerce.service.CustomerService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetWishlist() throws Exception {
        // Mock data
        Customer mockCustomer = new Customer(1L, "John Doe", "john.doe@gmail.com");
        Item mockItem1 = new Item(1L,"Item 1", 200);
        Item mockItem2 = new Item(2L,"Item 2", 100);
        List<Wishlist> mockWishlist = new ArrayList<>();
        mockWishlist.add(new Wishlist(1L, mockCustomer, mockItem1));
        mockWishlist.add(new Wishlist(2L, mockCustomer, mockItem2));

        // Mock service method
        when(customerService.getWishlistByCustomerId(anyLong())).thenReturn(mockWishlist);

        // Perform GET request and validate
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}/wishlist", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(2)));
    }
}

