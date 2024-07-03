package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.model.Wishlist;
import com.wsd.ecommerce.service.CustomerService;
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
    public List<Wishlist> getWishlist(@PathVariable Long customerId) {
        return customerService.getWishlistByCustomerId(customerId);
    }
}

