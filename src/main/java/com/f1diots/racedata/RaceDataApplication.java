package com.f1diots.racedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RaceDataApplication {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @GetMapping(path = "/raceData", produces = MediaType.APPLICATION_JSON_VALUE)
    RaceData raceData() {
        return new RaceData.RaceDataBuilder()
                .id("1")
                .trackName("kyalami_2020")
                .sessionType("FP")
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(RaceDataApplication.class, args);
    }

}
