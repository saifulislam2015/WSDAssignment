package com.wsd.ecommerce.repository;


import com.wsd.ecommerce.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT COALESCE(SUM(s.amount), 0) FROM sale s WHERE s.sale_date between :startOfDay AND :endOfDay", nativeQuery = true)
    Double findTotalSalesByDate(@Param("startOfDay") Timestamp startOfDay, @Param("endOfDay") Timestamp endOfDay);

    @Query("SELECT s.saleDate, SUM(s.amount) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate GROUP BY s.saleDate ORDER BY SUM(s.amount) DESC")
    List<Object[]> findMaxSaleDayInRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

//    @Query("SELECT s.itemName, SUM(s.amount) FROM Sale s GROUP BY s.itemName ORDER BY SUM(s.amount) DESC")
//    List<Object[]> findTopSellingItemsOfAllTime();
//
//    @Query("SELECT s.itemName, COUNT(s) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate GROUP BY s.itemName ORDER BY COUNT(s) DESC")
//    List<Object[]> findTopSellingItemsInLastMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

