package com.kaverzin.alexey.builder;

import com.kaverzin.alexey.model.exchangerate.Rates;

import java.math.BigDecimal;

public class RatesBuilder {
    Rates rates = new Rates();

    public static RatesBuilder rates(BigDecimal rUB) {
        return new RatesBuilder()
                .rUB(rUB);
    }

    public RatesBuilder rUB(BigDecimal rUB) {
        rates.setRUB(rUB);
        return this;
    }

    public Rates build() {
        return rates;
    }
}
