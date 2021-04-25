package com.f1diots.racedata;

import com.f1diots.racedata.db.RaceDataRepository;
import com.f1diots.racedata.model.RaceData;
import com.f1diots.racedata.task.FtpPuller;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class RaceDataApplication {

    @Autowired
    private RaceDataRepository raceDataDb;

    @Autowired
    private FtpPuller ftpPuller;

    private List<RaceData> cachedRaceData;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @GetMapping(path = "/raceData", produces = MediaType.APPLICATION_JSON_VALUE)
    List<RaceData> raceData(@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {
        log.info("Missed cache. Getting Race Data From DB");
        List<RaceData> raceData = raceDataDb.getSessions(limit, offset).collectList().block();
        cachedRaceData = raceData;
        return raceData;
    }

    @GetMapping(path = "/raceData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    RaceData raceData(@PathVariable String id) {
        log.info("Missed cache. Getting Race Data From DB");
        return raceDataDb.findById(id).block();
    }

    public static void main(String[] args) {
        SpringApplication.run(RaceDataApplication.class, args);
    }

}
