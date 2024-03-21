package com.currency.exchange;

import com.currency.exchange.entity.Currency;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import static com.currency.exchange.utils.TimeFormat.SIMPLE_TIME_FORMAT;

public class CurrencyDeserializer extends StdDeserializer<Currency> {

    public CurrencyDeserializer() {
        this(null);
    }

    public CurrencyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Currency deserialize(JsonParser jp, DeserializationContext context) {
        JsonNode node = tryCreateNode(jp);
        String name = node.get("txt").asText();
        Double rate = node.get("rate").doubleValue();
        Integer numericCode = node.get("r030").intValue();
        String currencyCode = node.get("cc").asText();
        String exchangeDate = node.get("exchangedate").asText();

        return new Currency(name, rate, numericCode, currencyCode, exchangeDate, prepareExchangeTime());
    }

    private JsonNode tryCreateNode(JsonParser jp) {
        try {
            return jp.getCodec().readTree(jp);
        } catch (IOException ex) {
            throw new RuntimeException("some problem with deserialize " + ex);
        }
    }

    private String prepareExchangeTime() {
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);

        return SIMPLE_TIME_FORMAT.format(timestamp);
    }
}