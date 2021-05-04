package com.f1diots.racedata.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RaceData {
    private String id;
    private Instant timestamp;
    private String trackName;
    private String sessionType;
    private SessionResult sessionResult;
    private List<Lap> laps;
    /*
    * "penalties": [
 {
 "carId": 1079,
 "driverIndex": 0,
 "reason": "Cutting",
 "penalty": "DriveThrough",
 "penaltyValue": 3,
 "violationInLap": 0,
 "clearedInLap": 1
 },
 {
 "carId": 1081,
 "driverIndex": 0,
 "reason": "PitSpeeding",
 "penalty": "StopAndGo_20",
 "penaltyValue": 20,
 "violationInLap": 4,
 "clearedInLap": 5
 }
 ]
}*/
    //penalties
    //post-race penalties
}
