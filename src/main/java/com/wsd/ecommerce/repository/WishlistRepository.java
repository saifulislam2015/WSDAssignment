package com.wsd.ecommerce.repository;

import com.wsd.ecommerce.dto.ItemDTO;
import com.wsd.ecommerce.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByCustomerId(Long customerId);

    @Query("SELECT new com.wsd.ecommerce.dto.ItemDTO(i.id, i.name) FROM Wishlist w " +
            "JOIN Item i ON w.item.id = i.id " +
            "WHERE w.customer.id = :customerId")
    List<ItemDTO> findWishlistByCustomerId(@Param("customerId") Long customerId);
}

