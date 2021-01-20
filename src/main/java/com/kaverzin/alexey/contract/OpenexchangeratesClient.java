package com.kaverzin.alexey.contract;

import com.kaverzin.alexey.model.exchangerate.ExchangeRatePojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openexchangerates", url = "${openexchangerates.feign.url}")
public interface OpenexchangeratesClient {

    @GetMapping("/{aDate}.json")
    ExchangeRatePojo getExchangeRate(
            @PathVariable("aDate") String aDate,
            @RequestParam(name = "symbols") String currency,
            @RequestParam(name = "app_id") String id);
}