package com.wsd.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleCountDTO {
    private Long id;
    private String name;
    private Double amount;
    private Long saleCount;
}
