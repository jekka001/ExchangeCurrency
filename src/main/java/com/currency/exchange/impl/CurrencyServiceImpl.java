package com.currency.exchange.impl;

import com.currency.exchange.CurrencyDeserializer;
import com.currency.exchange.dto.CurrencyDto;
import com.currency.exchange.entity.Currency;
import com.currency.exchange.mapper.CurrencyMapper;
import com.currency.exchange.repository.CurrencyRepository;
import com.currency.exchange.service.CurrencyService;
import com.currency.exchange.service.RequestService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static com.currency.exchange.utils.TimeFormat.SIMPLE_DATE_FORMAT;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final CurrencyRepository repository;
    private final CurrencyMapper mapper;
    private final RequestService requestService;
    private final ObjectMapper objectMapper;

    public CurrencyServiceImpl(CurrencyRepository repository, CurrencyMapper mapper, RequestService requestService) {
        this.repository = repository;
        this.mapper = mapper;
        this.requestService = requestService;
        this.objectMapper = new ObjectMapper();
        initObjectMapper();
    }

    private void initObjectMapper() {
        SimpleModule module = new SimpleModule();

        module.addDeserializer(Currency.class, new CurrencyDeserializer());

        objectMapper.registerModule(module);
    }

    @Override
    @Transactional
    public List<CurrencyDto> getAll() {
        String exchangeDate = prepareExchangeDate();
        List<Currency> currencies = repository.getAllByExchangeDate(exchangeDate);

        if (currencies.isEmpty()) {
            currencies = getCurrenciesByBank();
            logger.info("all exchanges currency were get");
        }

        return mapper.toListDto(currencies);
    }

    private String prepareExchangeDate() {
        Instant instant = Instant.now();
        Timestamp timestamp = new Timestamp(instant.toEpochMilli());

        return SIMPLE_DATE_FORMAT.format(timestamp);
    }

    private List<Currency> getCurrenciesByBank() {
        String json = requestService.getExchange();

        try {
            List<Currency> currencies = objectMapper.readValue(json, new TypeReference<>() {});

            return repository.saveAll(currencies);
        } catch (IOException ex) {
            throw new RuntimeException("some problem in bank -" + ex);
        }
    }

    @Override
    public List<CurrencyDto> getAll(String date) {
        List<Currency> currencies = repository.getAllByExchangeDate(date);

        return mapper.toListDto(currencies);
    }

    @Override
    @Transactional
    public boolean remove(String date) {
        repository.removeAllByExchangeDate(date);

        return true;
    }
}
