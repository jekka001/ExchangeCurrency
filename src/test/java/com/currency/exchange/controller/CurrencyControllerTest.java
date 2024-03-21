package com.currency.exchange.controller;

import com.currency.exchange.dto.CurrencyDto;
import com.currency.exchange.repository.CurrencyRepository;
import com.currency.exchange.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static com.currency.exchange.utils.Constants.SLASH;
import static com.currency.exchange.utils.URLUtils.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
    }

    @Test
    void readAll_is_ok() throws Exception {
        CurrencyDto currencyDto = new CurrencyDto(
                855,
                "Австралійський долар",
                25.5231,
                36,
                "AUD",
                "12.03.2024 14:16:13.413"
        );
        List<CurrencyDto> currencyDtoList = Collections.singletonList(currencyDto);
        when(currencyService.getAll()).thenReturn(currencyDtoList);

        mockMvc.perform(get(SLASH + API + CURRENCY + TODAY_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("*.id").value(currencyDto.getId()))
                .andExpect(jsonPath("*.name").value(currencyDto.getName()))
                .andExpect(jsonPath("*.rate").value(currencyDto.getRate()))
                .andExpect(jsonPath("*.numericCode").value(currencyDto.getNumericCode()))
                .andExpect(jsonPath("*.currencyCode").value(currencyDto.getCurrencyCode()))
                .andExpect(jsonPath("*.exchangeDateAndTime").value(currencyDto.getExchangeDateAndTime()));

        verify(currencyService, times(1)).getAll();
    }

    @Test
    void readAll_throw_exception() throws Exception {
        when(currencyService.getAll()).thenThrow(new RuntimeException("Some problem"));

        mockMvc.perform(get(SLASH + API + CURRENCY + TODAY_URL))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$").value("Some problem"));

        verify(currencyService, times(1)).getAll();
    }

    @Test
    void readAllByDate_is_ok() throws Exception {
        CurrencyDto currencyDto = new CurrencyDto(
                855,
                "Австралійський долар",
                25.5231,
                36,
                "AUD",
                "12.03.2024 14:16:13.413"
        );
        List<CurrencyDto> currencyDtoList = Collections.singletonList(currencyDto);
        when(currencyService.getAll("12.03.2024")).thenReturn(currencyDtoList);

        mockMvc.perform(get(SLASH + API + CURRENCY + DATE_URL).param("date", "12.03.2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("*.id").value(currencyDto.getId()))
                .andExpect(jsonPath("*.name").value(currencyDto.getName()))
                .andExpect(jsonPath("*.rate").value(currencyDto.getRate()))
                .andExpect(jsonPath("*.numericCode").value(currencyDto.getNumericCode()))
                .andExpect(jsonPath("*.currencyCode").value(currencyDto.getCurrencyCode()))
                .andExpect(jsonPath("*.exchangeDateAndTime").value(currencyDto.getExchangeDateAndTime()));

        verify(currencyService, times(1)).getAll("12.03.2024");
    }

    @Test
    void readAllByDate_throw_exception() throws Exception {
        when(currencyService.getAll("12.03.2024")).thenThrow(new RuntimeException("Some problem"));

        mockMvc.perform(get(SLASH + API + CURRENCY + DATE_URL).param("date", "12.03.2024"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$").value("Some problem"));

        verify(currencyService, times(1)).getAll("12.03.2024");
    }

    @Test
    void delete_is_ok() throws Exception {
        when(currencyService.remove("12.03.2024")).thenReturn(true);

        mockMvc.perform(delete(SLASH + API + CURRENCY + DELETE_URL).param("date", "12.03.2024"))
                .andExpect(status().isOk());

        verify(currencyService, times(1)).remove("12.03.2024");
    }

    @Test
    void delete_throw_exception() throws Exception {
        when(currencyService.remove("12.03.2024")).thenThrow(new RuntimeException("Some problem"));

        mockMvc.perform(delete(SLASH + API + CURRENCY + DELETE_URL).param("date", "12.03.2024"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$").value("Some problem"));

        verify(currencyService, times(1)).remove("12.03.2024");
    }
}