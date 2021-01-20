package com.kaverzin.alexey;

import com.kaverzin.alexey.service.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UnitTests {

    @Autowired
    private MainController mainController;

    @Value("${openexchangerates.feign.url}") private String OPENEXCHANGERATES_API_URL;
    @Value("${openexchangerates.feign.symbols}") private String CURRENCY;
    @Value("${openexchangerates.feign.app_id}") private String Id;
    @Value("${giphy.feign.url}") private String GIPHY_API_URL;
    @Value("${giphy.feign.rating}") private String RATING;
    @Value("${giphy.feign.api_key}") private String API_KEY;

    String today = "today";
    String yesterday = "yesterday";

    String tagRich = "rich";
    String tagBroke = "broke";
    String tagStable = "stable";

    HashMap<String, BigDecimal> exchangeRateRich = new HashMap<>();
    HashMap<String, String> timestampRich = new HashMap<>();
    HashMap<String, BigDecimal> exchangeRateBroke = new HashMap<>();
    HashMap<String, String> timestampBroke = new HashMap<>();
    HashMap<String, BigDecimal> exchangeRateStable = new HashMap<>();
    HashMap<String, String> timestampStable = new HashMap<>();

    public UnitTests() {

        exchangeRateRich.put(today, BigDecimal.valueOf(74.193976));
        exchangeRateRich.put(yesterday, BigDecimal.valueOf(74.0836));
        timestampRich.put(today, "2021-01-10");
        timestampRich.put(yesterday, "2021-01-09");

        exchangeRateBroke.put(today, BigDecimal.valueOf(74.0836));
        exchangeRateBroke.put(yesterday, BigDecimal.valueOf(74.193976));
        timestampBroke.put(today, "2021-01-09");
        timestampBroke.put(yesterday, "2021-01-10");

        exchangeRateStable.put(today, BigDecimal.valueOf(74.0836));
        exchangeRateStable.put(yesterday, BigDecimal.valueOf(74.0836));
        timestampStable.put(today, "2021-01-09");
        timestampStable.put(yesterday, "2021-01-08");
    }

    @Test
    public void testOpenexchangeratesUrl() {
        String todayOpenexchangeratesUrl = OPENEXCHANGERATES_API_URL + timestampRich.get(today) + ".json?symbols=" + CURRENCY + "&app_id=" + Id;
        assertEquals("https://openexchangerates.org/api/historical/2021-01-10.json?symbols=RUB&app_id=52f5068d064f49fe81723a05eb97dd8c", todayOpenexchangeratesUrl);
        String yesterdayOpenexchangeratesUrl = OPENEXCHANGERATES_API_URL + timestampRich.get(yesterday) + ".json?symbols=" + CURRENCY + "&app_id=" + Id;
        assertEquals("https://openexchangerates.org/api/historical/2021-01-09.json?symbols=RUB&app_id=52f5068d064f49fe81723a05eb97dd8c", yesterdayOpenexchangeratesUrl);
    }

    @Test
    public void testSetOfTag_Rich() {
        assertEquals(mainController.setOfTag(exchangeRateRich.get(today), exchangeRateRich.get(yesterday)), tagRich);
    }

    @Test
    public void testGphyUrl_Rich() {
        String giphyUrl = GIPHY_API_URL + "random?tag=" + tagRich + "&rating=" + RATING +  "&api_key=" + API_KEY;
        assertEquals("https://api.giphy.com/v1/gifs/random?tag=rich&rating=g&api_key=5oazV01gGpeEJfHClSRFoczmGGbzKYN8", giphyUrl);
    }

    @Test
    public void testDynamicsOfRate_Rich() {
        BigDecimal dynamicsOfRate = exchangeRateRich.get(today).subtract(exchangeRateRich.get(yesterday));
        assertEquals(mainController.setDynamicsOfRate(exchangeRateRich.get(today), exchangeRateRich.get(yesterday)), dynamicsOfRate);
    }

    @Test
    public void testSetOfTag_Broke() {
        assertEquals(mainController.setOfTag(exchangeRateBroke.get(today), exchangeRateBroke.get(yesterday)), tagBroke);
    }

    @Test
    public void testGphyUrl_Broke() {
        String giphyUrl = GIPHY_API_URL + "random?tag=" + tagBroke + "&rating=" + RATING +  "&api_key=" + API_KEY;
        assertEquals("https://api.giphy.com/v1/gifs/random?tag=broke&rating=g&api_key=5oazV01gGpeEJfHClSRFoczmGGbzKYN8", giphyUrl);
    }

    @Test
    public void testDynamicsOfRate_Broke() {
        BigDecimal dynamicsOfRate = exchangeRateBroke.get(today).subtract(exchangeRateBroke.get(yesterday));
        assertEquals(mainController.setDynamicsOfRate(exchangeRateBroke.get(today), exchangeRateBroke.get(yesterday)), dynamicsOfRate);
    }

    @Test
    public void testSetOfTag_Stable() {
        assertEquals(mainController.setOfTag(exchangeRateStable.get(today), exchangeRateStable.get(yesterday)), tagStable);
    }

    @Test
    public void testDynamicsOfRate_Stable() {
        BigDecimal dynamicsOfRate = exchangeRateStable.get(today).subtract(exchangeRateStable.get(yesterday));
        assertEquals(mainController.setDynamicsOfRate(exchangeRateStable.get(today), exchangeRateStable.get(yesterday)), dynamicsOfRate);
    }
}