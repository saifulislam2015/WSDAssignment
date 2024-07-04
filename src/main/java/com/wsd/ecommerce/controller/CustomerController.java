package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.dto.CustomerWishlistDTO;
import com.wsd.ecommerce.model.Wishlist;
import com.wsd.ecommerce.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/{customerId}/wishlist")
    public ResponseEntity<CustomerWishlistDTO> getWishlist(@PathVariable Long customerId) {
        return new ResponseEntity<>(customerService.getWishlistByCustomerId(customerId), HttpStatus.OK);
    }
}

