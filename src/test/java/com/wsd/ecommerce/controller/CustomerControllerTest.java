package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.dto.CustomerWishlistDTO;
import com.wsd.ecommerce.dto.ItemDTO;
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
        List<ItemDTO> itemDTOS = new ArrayList<>();
        itemDTOS.add(new ItemDTO(1L,"Item 1"));
        itemDTOS.add(new ItemDTO(2L,"Item 2"));

        CustomerWishlistDTO customerWishlistDTO = new CustomerWishlistDTO("John Doe", itemDTOS);

        // Mock service method
        when(customerService.getWishlistByCustomerId(anyLong())).thenReturn(customerWishlistDTO);

        // Perform GET request and validate
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}/wishlist", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

