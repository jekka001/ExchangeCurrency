package com.currency.exchange.mapper;

import com.currency.exchange.dto.CurrencyDto;
import com.currency.exchange.entity.Currency;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;

import static com.currency.exchange.utils.Constants.COMPONENT_MODEL;
import static com.currency.exchange.utils.Constants.SPACE;
import static com.currency.exchange.utils.TimeFormat.DATE_TIME_FORMATTER;

@Mapper(componentModel = COMPONENT_MODEL)
public interface CurrencyMapper {

    Currency toEntity(CurrencyDto currencyDto);

    default CurrencyDto toDto(Currency currency) {
        return new CurrencyDto(
                currency.getId(),
                currency.getName(),
                currency.getRate(),
                currency.getNumericCode(),
                currency.getCurrencyCode(),
                prepareDateAndTime(currency.getExchangeDate(), currency.getExchangeTime())
        );
    }

    private String prepareDateAndTime(String exchangeDate, String exchangeTime) {
        String dateTime = exchangeDate + SPACE + exchangeTime;
        LocalDateTime exchangeDateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);

        return exchangeDateTime.toString();
    }

    List<Currency> toListEntity(List<CurrencyDto> exchangesDto);

    List<CurrencyDto> toListDto(List<Currency> currencies);
}
