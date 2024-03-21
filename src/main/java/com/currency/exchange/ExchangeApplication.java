package com.currency.exchange;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.currency.exchange.utils.Constants.NAME_SWAGGER_SERVER;
import static com.currency.exchange.utils.Constants.SLASH;

@OpenAPIDefinition(servers = {@Server(url = SLASH, description = NAME_SWAGGER_SERVER)})
@SpringBootApplication
public class ExchangeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExchangeApplication.class, args);
    }

}
