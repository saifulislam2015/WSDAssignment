package com.wsd.ecommerce.service;


import com.wsd.ecommerce.model.Wishlist;
import com.wsd.ecommerce.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final WishlistRepository wishlistRepository;

    public CustomerService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getWishlistByCustomerId(Long customerId) {
        return wishlistRepository.findByCustomerId(customerId);
    }
}

