package com.kaverzin.alexey.contract;

import com.kaverzin.alexey.model.giphypojo.GiphyPojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphy", url = "${giphy.feign.url}")
public interface GiphyClient {

    @GetMapping("/random")
    GiphyPojo getGif(
            @RequestParam(name = "tag") String tag,
            @RequestParam(name = "rating") String rating,
            @RequestParam(name = "api_key") String api_key);
}