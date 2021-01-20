package com.kaverzin.alexey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AlekseyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlekseyApplication.class, args);
    }

}
