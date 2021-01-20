package com.kaverzin.alexey;

import com.kaverzin.alexey.model.exchangerate.ExchangeRatePojo;
import com.kaverzin.alexey.model.exchangerate.Rates;
import com.kaverzin.alexey.service.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.kaverzin.alexey.builder.ExchangeRatePojoBuilder.exchangeRatePojo;
import static com.kaverzin.alexey.builder.RatesBuilder.rates;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class AlexeyApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController mainController;

    @Value("${openexchangerates.feign.symbols}") private String CURRENCY;
    @Value("${openexchangerates.feign.app_id}") private String Id;
    String testDate = "2021-01-09";
    String testTimestamp = "1610236743";
    BigDecimal testExchangeRate = BigDecimal.valueOf(74.0836);

    @Test
    public void testExchangeRatePojo() {
        Rates todayRate = rates(testExchangeRate).build();
        ExchangeRatePojo todayExchangeRatePojo = exchangeRatePojo(testTimestamp, todayRate).build();
        assertEquals(mainController.fillOutExchangeRatePojo(testDate, CURRENCY, Id).toString(), todayExchangeRatePojo.toString());
    }

    @Test
    public void testReturnsIndexView() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

}