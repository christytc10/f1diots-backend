package com.f1diots.racedata;

import com.f1diots.racedata.model.AccCar;
import com.f1diots.racedata.model.RaceData;
import com.f1diots.racedata.task.FtpPuller;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class RaceDataApplication {

    //@Autowired
    //private RaceDataRepository raceDataDb;

    @Autowired
    private FtpPuller ftpPuller;

    private Map<String, RaceData> cachedRaceData = new HashMap<>();

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @GetMapping(path = "/raceData", produces = MediaType.APPLICATION_JSON_VALUE)
    List<RaceData> raceData(@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {
        //return raceDataDb.getSessions(limit, offset).collectList().block();
        return null;
    }

    @GetMapping(path = "/raceData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    RaceData raceDataById(@PathVariable String id) {
        RaceData raceData = getRaceData(id);
        decorateRaceData(raceData);
        return raceData;
    }

    private void decorateRaceData(RaceData raceData) {
        raceData.getSessionResult().getLeaderBoardLines().forEach(leaderBoardLine -> {
            int carModel = leaderBoardLine.getCar().getCarModel();
            leaderBoardLine.getCar().setCarDetails(AccCar.byId(carModel));
        });
    }

    private RaceData getRaceData(String id) {
        if (cachedRaceData.containsKey(id)){
            log.info("Hit cache. Returning Race Data for {} From cache", id);
            return cachedRaceData.get(id);
        }
        log.info("Missed cache. Getting Race Data From DB");
        //RaceData raceData = raceDataDb.findById(id).block();
        //cachedRaceData.putIfAbsent(id, raceData);
        //return raceData;
        return null;
    }

    public static void main(String[] args) {
        SpringApplication.run(RaceDataApplication.class, args);
    }

}
