package com.currency.exchange.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.currency.exchange.utils.Table.CURRENCY;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = CURRENCY, indexes = {@Index(name = "date", columnList = "exchangeDate")})
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty("txt")
    private String name;
    private Double rate;
    @JsonProperty("r030")
    private Integer numericCode;
    @JsonProperty("cc")
    private String currencyCode;
    @JsonProperty("exchangedate")
    private String exchangeDate;
    private String exchangeTime;

    public Currency(String name, Double rate, Integer numericCode, String currencyCode, String exchangeDate, String exchangeTime) {
        this.name = name;
        this.rate = rate;
        this.numericCode = numericCode;
        this.currencyCode = currencyCode;
        this.exchangeDate = exchangeDate;
        this.exchangeTime = exchangeTime;
    }
}
