package com.currency.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {
    private Integer id;
    private String name;
    private Double rate;
    private Integer numericCode;
    private String currencyCode;
    private String exchangeDateAndTime;
}
