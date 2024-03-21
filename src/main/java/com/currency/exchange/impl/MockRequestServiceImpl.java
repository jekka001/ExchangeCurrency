package com.currency.exchange.impl;

import com.currency.exchange.service.RequestService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Profile("MOCK")
public class MockRequestServiceImpl implements RequestService {
    private final Logger logger = LoggerFactory.getLogger(MockRequestServiceImpl.class);

    @Override
    public String getExchange() {
        logger.info("start to get exchanges currency from MOCK file");

        return getExchangeFromFile();
    }

    private String getExchangeFromFile() {
        return tryCreateString();
    }

    private String tryCreateString() {
        try {
            File file = new ClassPathResource("file/mockExchange.json").getFile();

            return FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException ex) {
            throw new RuntimeException("some problem in File - " + ex);
        }
    }
}
