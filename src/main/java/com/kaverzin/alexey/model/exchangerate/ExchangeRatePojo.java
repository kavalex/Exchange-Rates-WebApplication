package com.kaverzin.alexey.model.exchangerate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"disclaimer",
		"license",
		"timestamp",
		"base",
		"rates"
})
public class ExchangeRatePojo {
	@JsonProperty("disclaimer")
	private String disclaimer;
	@JsonProperty("license")
	private String license;
	@JsonProperty("timestamp")
	private String timestamp;
	@JsonProperty("base")
	private String base;
	@JsonProperty("rates")
	private Rates rates;

	@JsonProperty("disclaimer")
	public String getDisclaimer() {
		return disclaimer;
	}

	@JsonProperty("disclaimer")
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	@JsonProperty("license")
	public String getLicense() {
		return license;
	}

	@JsonProperty("license")
	public void setLicense(String license) {
		this.license = license;
	}

	@JsonProperty("timestamp")
	public String getTimestamp(){
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp(String timestamp) {
		LocalDateTime dateTime = LocalDateTime.ofEpochSecond(Long.parseLong(timestamp), 0, ZoneOffset.UTC);
		this.timestamp = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	@JsonProperty("base")
	public String getBase() {
		return base;
	}

	@JsonProperty("base")
	public void setBase(String base) {
		this.base = base;
	}

	@JsonProperty("rates")
	public Rates getRates() {
		return rates;
	}

	@JsonProperty("rates")
	public void setRates(Rates rates) {
		this.rates = rates;
	}

	@Override
 	public String toString(){
		return 
			"ExchangeRatePojo{" +
			"disclaimer = '" + disclaimer + '\'' +
			",license = '" + license + '\'' +
			",timestamp = '" + timestamp + '\'' +
			",base = '" + base + '\'' +
			",rates = '" + rates + '\'' +
			"}";
		}
}
