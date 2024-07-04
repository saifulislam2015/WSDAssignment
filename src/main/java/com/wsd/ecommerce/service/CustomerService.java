package com.wsd.ecommerce.service;


import com.wsd.ecommerce.dto.CustomerWishlistDTO;
import com.wsd.ecommerce.dto.ItemDTO;
import com.wsd.ecommerce.model.Customer;
import com.wsd.ecommerce.model.Wishlist;
import com.wsd.ecommerce.repository.CustomerRepository;
import com.wsd.ecommerce.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(WishlistRepository wishlistRepository, CustomerRepository customerRepository) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
    }

    public CustomerWishlistDTO getWishlistByCustomerId(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            List<ItemDTO> dto = wishlistRepository.findWishlistByCustomerId(customerId);
            return new CustomerWishlistDTO(customer.get().getName(), dto);
        }
        return null;
    }
}

