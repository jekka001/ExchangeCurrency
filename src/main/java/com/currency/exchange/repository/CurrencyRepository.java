package com.currency.exchange.repository;

import com.currency.exchange.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    List<Currency> getAllByExchangeDate(String date);

    void removeAllByExchangeDate(String date);
}
