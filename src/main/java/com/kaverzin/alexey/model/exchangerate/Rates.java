package com.kaverzin.alexey.model.exchangerate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"RUB"})
public class Rates {

	@JsonProperty("RUB")
	private BigDecimal rUB;

	@JsonProperty("RUB")
	public BigDecimal getRUB() {
		return rUB;
	}

	@JsonProperty("RUB")
	public void setRUB(BigDecimal rUB) {
		this.rUB = rUB;
	}

	@Override
	public String toString() {
		return "RUB: " + rUB;
	}

}
