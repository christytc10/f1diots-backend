package com.f1diots.racedata.db.transformer;

import com.f1diots.racedata.db.model.Lap;
import com.f1diots.racedata.db.model.LeaderBoardLine;
import com.f1diots.racedata.db.model.RaceSession;
import com.f1diots.racedata.db.model.SessionCar;
import com.f1diots.racedata.model.RaceData;

import java.util.List;
import java.util.stream.Collectors;

public class RaceDataTransformer {
    private RaceDataTransformer() {
        //hide public constructor
    }

    public static RaceSession transform(RaceData raceData) {
        final RaceSession raceSession = RaceSession.builder()
                .id(raceData.getId())
                .sessionType(raceData.getSessionType())
                .timestamp(raceData.getTimestamp())
                .trackName(raceData.getTrackName())
                .wet(raceData.getSessionResult().isWet())
                .leaderBoardLines(buildLeaderBoardLines(raceData))
                .build();
        return raceSession;
    }

    private static List<LeaderBoardLine> buildLeaderBoardLines(RaceData raceData) {
        return raceData.getSessionResult().getLeaderBoardLines().stream().map(lbl -> {
            String sessionId = raceData.getId();
            Long carId = lbl.getCar().getCarId();



            List<Lap> carLaps = raceData.getLaps().stream()
                    .filter(lap -> carId.equals(lap.getCarId()))
                    .map(lap -> Lap.builder()
                            .carId(lap.getCarId())
                            //.driverIndex() //FIXME - map to an actual driver entity
                            .laptime(lap.getLaptime())
                            .validForBest(lap.getValidForBest())
                            //.raceSession() //FIXME - map to session after
                            .build())
                    .collect(Collectors.toList());
            SessionCar car = SessionCar.builder()
                    .build();
            return LeaderBoardLine.builder().car(car).laps(carLaps).build();
        }).collect(Collectors.toList());
    }
}
