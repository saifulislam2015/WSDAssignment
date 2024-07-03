package com.wsd.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaxSaleDayDTO {
    private Date saleDate;
    private Double totalAmount;
}

