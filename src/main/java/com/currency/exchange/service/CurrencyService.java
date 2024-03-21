package com.currency.exchange.service;

import com.currency.exchange.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {
    List<CurrencyDto> getAll();

    List<CurrencyDto> getAll(String date);

    boolean remove(String date);
}
