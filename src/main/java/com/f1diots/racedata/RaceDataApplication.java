package com.f1diots.racedata;

import com.f1diots.racedata.db.RaceSessionRepository;
import com.f1diots.racedata.db.model.RaceSession;
import com.f1diots.racedata.task.FtpPuller;
import com.f1diots.racedata.task.model.AccCar;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class RaceDataApplication {

    @Autowired
    private RaceSessionRepository raceDataDb;

    @Autowired
    private FtpPuller ftpPuller;

    private Map<String, RaceSession> cachedRaceData = new HashMap<>();

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @GetMapping(path = "/raceData", produces = MediaType.APPLICATION_JSON_VALUE)
    List<RaceSession> raceData(@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("timestamp").descending());
        return raceDataDb.findAll(pageable).getContent();
    }

    @GetMapping(path = "/raceData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    RaceSession raceDataById(@PathVariable String id) {
        return raceDataDb.findById(id).orElseThrow(NullPointerException::new);//TODO - throw a 404
    }

    @GetMapping(path = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AccCar> cars() {
        return Arrays.asList(AccCar.values());
    }

    public static void main(String[] args) {
        SpringApplication.run(RaceDataApplication.class, args);
    }

}
