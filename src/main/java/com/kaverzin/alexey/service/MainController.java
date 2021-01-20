package com.kaverzin.alexey.service;

import com.kaverzin.alexey.contract.GiphyClient;
import com.kaverzin.alexey.contract.OpenexchangeratesClient;
import com.kaverzin.alexey.model.exchangerate.ExchangeRatePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Controller
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private static final int PRECISION = 6;
    @Value("${openexchangerates.feign.symbols}") private String CURRENCY;
    @Value("${openexchangerates.feign.app_id}") private String Id;
    @Value("${giphy.feign.rating}") private String RATING;
    @Value("${giphy.feign.api_key}") private String API_KEY;
    private final OpenexchangeratesClient openexchangeratesClient;
    private final GiphyClient giphyClient;
    LocalDateTime todayDateUtc;
    String todayDate;
    String yesterdayDate;
    String today = "today";
    String yesterday = "yesterday";
    String rich = "rich";
    String broke = "broke";
    String stable = "stable";
    HashMap<String, ExchangeRatePojo> exchangeRatePojo = new HashMap<>();
    HashMap<String, BigDecimal> exchangeRate = new HashMap<>();
    HashMap<String, String> timestamp = new HashMap<>();
    BigDecimal dynamicsOfRate;
    String tagOfGiphy;
    String urlOfGiphy;
    String summaryDynamics;
    private String summaryToday;
    private String summaryYesterday;
    private String summaryStatus;

    public MainController(OpenexchangeratesClient openexchangeratesClient, GiphyClient giphyClient) {
        this.openexchangeratesClient = openexchangeratesClient;
        this.giphyClient = giphyClient;
        LOGGER.info("+ MainController");
    }

    @GetMapping("/")
    public String index(Model model) {
        LOGGER.info("+ started endpoint '/'");

        todayDateUtc = LocalDateTime.now(ZoneOffset.UTC);
        todayDate = DateTimeFormatter.ISO_LOCAL_DATE.format(todayDateUtc);
        yesterdayDate = DateTimeFormatter.ISO_LOCAL_DATE.format(todayDateUtc.minusDays(1));

        fillOutDataOfExchangeRates();

        dynamicsOfRate = setDynamicsOfRate(exchangeRate.get(today), exchangeRate.get(yesterday));
        tagOfGiphy = setOfTag(exchangeRate.get(today), exchangeRate.get(yesterday));
        urlOfGiphy = setUrlOfGiphy(RATING, API_KEY);

        summaryDynamics = String.format("dynamics is %+.4f %s", bigDecimalToFloat(dynamicsOfRate), CURRENCY);
        LOGGER.info(" - summaryDynamics: {}", summaryDynamics);
        summaryToday = formatString(today, timestamp, exchangeRate, CURRENCY);
        summaryYesterday = formatString(yesterday, timestamp, exchangeRate, CURRENCY);
        summaryStatus = String.format("You are %s!", tagOfGiphy);
        LOGGER.info(" - summaryStatus: {}", summaryStatus);

        model.addAttribute("summaryDynamics", summaryDynamics);
        model.addAttribute("summaryToday", summaryToday);
        model.addAttribute("summaryYesterday", summaryYesterday);
        model.addAttribute("summaryStatus", summaryStatus);
        model.addAttribute("giphyUrl", urlOfGiphy);
        return "index";
    }

    public ExchangeRatePojo fillOutExchangeRatePojo(String dates, String CURRENCY, String Id) {
        LOGGER.info(" - fillOutExchangeRatePojo on: {}", dates);
        return openexchangeratesClient.getExchangeRate(dates, CURRENCY, Id);
    }

    public void fillOutDataOfExchangeRates() {
        for (int i = 0; i < 2; i++) {
            String aDay = (i == 0) ? today : yesterday;
            String date = (i == 0) ? todayDate : yesterdayDate;
            exchangeRatePojo.put(aDay, fillOutExchangeRatePojo(date, CURRENCY, Id));
            timestamp.put(aDay, exchangeRatePojo.get(aDay).getTimestamp());
            exchangeRate.put(aDay, exchangeRatePojo.get(aDay).getRates().getRUB());
        }
        LOGGER.info(" - timestamp: {}", timestamp);
        LOGGER.info(" - exchangeRate: {}", exchangeRate);
    }

    public BigDecimal setDynamicsOfRate(BigDecimal today, BigDecimal yesterday){
        BigDecimal subtract = today.subtract(yesterday);
        LOGGER.info(" - setDynamicsOfRate: {}", subtract);
        return subtract;
    }

    public String setOfTag(BigDecimal today, BigDecimal yesterday){
        int resultOfCompare = today.compareTo(yesterday);
        String status = (resultOfCompare > 0) ? rich : (resultOfCompare < 0) ? broke : stable;
        LOGGER.info(" - setOfTag: {}", status);
        return status;
    }

    public String setUrlOfGiphy(String RATING, String API_KEY) {
        String url = "";
        if (!tagOfGiphy.equals("STABLE")) {
            url = giphyClient.getGif(tagOfGiphy, RATING, API_KEY).getData().getFixedHeightSmallUrl();
        }
        LOGGER.info(" - setUrlOfGiphy: {}", url);
        return url;
    }

    public float bigDecimalToFloat(BigDecimal bigDecimal){
        return bigDecimal.setScale(PRECISION, RoundingMode.HALF_UP).floatValue();
    }

    public String formatString (String aDay, HashMap<String, String> timestamp, HashMap<String, BigDecimal> exchangeRate, String aCurrensy) {
        String aTimestamp = timestamp.get(aDay);
        Float aExchangeRate = bigDecimalToFloat(exchangeRate.get(aDay));
        String anyString = String.format("%s on %s is %.4f %s for one USD",aDay, aTimestamp, aExchangeRate, aCurrensy);
        LOGGER.info(" - {}", anyString);
        return anyString;
    }
}
