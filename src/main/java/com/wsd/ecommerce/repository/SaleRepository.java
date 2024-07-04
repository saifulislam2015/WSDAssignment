package com.wsd.ecommerce.repository;


import com.wsd.ecommerce.dto.SaleCountDTO;
import com.wsd.ecommerce.dto.SaleItemDto;
import com.wsd.ecommerce.model.Sale;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT COALESCE(SUM(s.amount), 0) FROM sale s WHERE s.sale_date between :startOfDay AND :endOfDay", nativeQuery = true)
    Double findTotalSalesByDate(@Param("startOfDay") Timestamp startOfDay, @Param("endOfDay") Timestamp endOfDay);

    @Query(value = "SELECT DATE(s.sale_date) AS saleDate, SUM(s.amount) AS totalAmount FROM sale s " +
            "WHERE sale_date BETWEEN :startDate AND :endDate GROUP BY saleDate ORDER BY totalAmount DESC LIMIT 1", nativeQuery = true)
    List<Tuple> findMaxSaleDayInRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT new com.wsd.ecommerce.dto.SaleItemDto(s.item.id, i.name, SUM(s.amount)) FROM Sale s INNER JOIN s.item i GROUP BY s.item.id, i.name ORDER BY SUM(s.amount) DESC")
    List<SaleItemDto> findTopSellingItemsOfAllTime(Pageable pageable);

    @Query("SELECT new com.wsd.ecommerce.dto.SaleCountDTO(s.item.id, i.name, SUM(s.amount), COUNT(s.id)) FROM Sale s INNER JOIN s.item i WHERE s.saleDate >= :date GROUP BY s.item.id, i.name ORDER BY COUNT(s.id) DESC,SUM(s.amount) DESC LIMIT 5")
    List<SaleCountDTO> findTopSellingItemsBySalesCountLastMonth(@Param("date") Timestamp date);
}

