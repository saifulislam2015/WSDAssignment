package com.wsd.ecommerce.repository;


import com.wsd.ecommerce.dto.MaxSaleDayDTO;
import com.wsd.ecommerce.model.Sale;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT COALESCE(SUM(s.amount), 0) FROM sale s WHERE s.sale_date between :startOfDay AND :endOfDay", nativeQuery = true)
    Double findTotalSalesByDate(@Param("startOfDay") Timestamp startOfDay, @Param("endOfDay") Timestamp endOfDay);

    @Query(value = "SELECT DATE(s.sale_date) AS saleDate, SUM(s.amount) AS totalAmount FROM sale s " +
            "WHERE sale_date BETWEEN :startDate AND :endDate GROUP BY saleDate ORDER BY totalAmount DESC LIMIT 1", nativeQuery = true)
    List<Tuple> findMaxSaleDayInRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

//    @Query("SELECT s.itemName, SUM(s.amount) FROM Sale s GROUP BY s.itemName ORDER BY SUM(s.amount) DESC")
//    List<Object[]> findTopSellingItemsOfAllTime();
//
//    @Query("SELECT s.itemName, COUNT(s) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate GROUP BY s.itemName ORDER BY COUNT(s) DESC")
//    List<Object[]> findTopSellingItemsInLastMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

