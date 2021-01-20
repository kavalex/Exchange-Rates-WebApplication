package com.kaverzin.alexey.builder;

import com.kaverzin.alexey.model.exchangerate.ExchangeRatePojo;
import com.kaverzin.alexey.model.exchangerate.Rates;

public class ExchangeRatePojoBuilder {
    ExchangeRatePojo exchangeRatePojo = new ExchangeRatePojo();

    public static ExchangeRatePojoBuilder exchangeRatePojo(String timestamp, Rates rates) {
        return new ExchangeRatePojoBuilder()
                .disclaimer("Usage subject to terms: https://openexchangerates.org/terms")
                .license("https://openexchangerates.org/license")
                .timestamp(timestamp)
                .base("USD")
                .Rates(rates);
    }

    public ExchangeRatePojoBuilder disclaimer(String disclaimer) {
        exchangeRatePojo.setDisclaimer(disclaimer);
        return this;
    }

    public ExchangeRatePojoBuilder license(String license) {
        exchangeRatePojo.setLicense(license);
        return this;
    }

    public ExchangeRatePojoBuilder timestamp(String timestamp) {
        exchangeRatePojo.setTimestamp(timestamp);
        return this;
    }

    public ExchangeRatePojoBuilder base(String base) {
        exchangeRatePojo.setBase(base);
        return this;
    }

    public ExchangeRatePojoBuilder Rates(Rates rates) {
        exchangeRatePojo.setRates(rates);
        return this;
    }

    public ExchangeRatePojo build() {
        return exchangeRatePojo;
    }
}
