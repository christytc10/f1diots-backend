package com.f1diots.racedata;

import com.f1diots.racedata.db.RaceSessionRepository;
import com.f1diots.racedata.db.model.RaceSession;
import com.f1diots.racedata.task.model.AccCar;
import com.f1diots.racedata.task.model.RaceData;
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
import java.util.Optional;

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

    private Map<String, RaceData> cachedRaceData = new HashMap<>();

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @GetMapping(path = "/raceData", produces = MediaType.APPLICATION_JSON_VALUE)
    List<RaceSession> raceData(@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {
        return raceDataDb.findAll(); //TODO - paginate sort
    }

    @GetMapping(path = "/raceData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    RaceSession raceDataById(@PathVariable String id) {
        return raceDataDb.findById(id).orElseThrow(NullPointerException::new);//TODO - throw a 404
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
