package com.currency.exchange.impl;

import com.currency.exchange.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.currency.exchange.utils.URLUtils.GET;

@Service
@Profile("DEV")
public class RequestServiceImpl implements RequestService {
    private final Logger logger = LoggerFactory.getLogger(RequestServiceImpl.class);

    @Value("${exchange.bank.url}")
    private String exchangeBankUrl;

    @Override
    public String getExchange() {
        logger.info("start to get exchanges currency from NBU");
        HttpURLConnection con = getConnection();

        StringBuffer content = getContent(con);

        con.disconnect();
        return content.toString();
    }

    private HttpURLConnection getConnection() {
        try {
            return tryCreateConnection();
        } catch (IOException ex) {
            throw new RuntimeException("some problem with bank - " + exchangeBankUrl + ex);
        }
    }

    private HttpURLConnection tryCreateConnection() throws IOException {
        URL url = new URL(exchangeBankUrl);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(GET);

        return con;
    }

    private StringBuffer getContent(HttpURLConnection con) {
        try {
            return tryCreateContent(con);
        } catch (IOException ex) {
            throw new RuntimeException("some problem with content " + ex);
        }
    }

    private StringBuffer tryCreateContent(HttpURLConnection con) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer content = new StringBuffer();
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null) {
            content.append(inputLine);
        }

        bufferedReader.close();
        return content;
    }
}
