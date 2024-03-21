package com.currency.exchange.controller;

import com.currency.exchange.dto.CurrencyDto;
import com.currency.exchange.service.CurrencyService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static com.currency.exchange.utils.URLUtils.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(API + CURRENCY)
public class CurrencyController {
    private final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private CurrencyService currencyService;

    @GetMapping(path = TODAY_URL, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CurrencyDto>> readAll() {
        logger.info("try to get today currency");
        List<CurrencyDto> currencyDto = currencyService.getAll();

        logger.info("result today request - " + currencyDto);
        return new ResponseEntity<>(currencyDto, HttpStatus.OK);
    }

    @GetMapping(path = DATE_URL, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CurrencyDto>> readAll(@RequestParam String date) {
        logger.info("try to get currency by date");
        List<CurrencyDto> currencyDto = currencyService.getAll(date);

        logger.info("result request by date - " + currencyDto);
        return new ResponseEntity<>(currencyDto, HttpStatus.OK);
    }

    @DeleteMapping(path = DELETE_URL, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@RequestParam String date) {
        logger.info("try to delete currency");

        boolean isDeleted = currencyService.remove(date);

        logger.info("result delete request - " + (isDeleted ? "deleted" : "problems"));
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        logger.error(e.getMessage(), this.getClass());

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
