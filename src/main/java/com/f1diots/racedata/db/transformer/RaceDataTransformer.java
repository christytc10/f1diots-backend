package com.f1diots.racedata.db.transformer;

import com.f1diots.racedata.db.model.*;
import com.f1diots.racedata.task.model.RaceData;

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
                            .driverIndex(lap.getDriverIndex()) //FIXME - map to an actual driver entity
                            .lapTime(lap.getLaptime())
                            .validForBest(lap.getValidForBest())
                            .build())
                    .collect(Collectors.toList());

            List<AccDriver> drivers = lbl.getCar().getDrivers().stream()
                    .map(d -> AccDriver.builder()
                            .firstName(d.getFirstName())
                            .lastName(d.getLastName())
                            .playerId(d.getPlayerId())
                            .shortName(d.getShortName())
                            .build()
                    )
                    .collect(Collectors.toList());
            SessionCar car = SessionCar.builder()
                    .sessionCarId(SessionCarId.builder().carId(carId.intValue()).sessionId(sessionId).build())
                    .laps(carLaps)
                    .carGuid(lbl.getCar().getCarGuid())
                    .carModel(lbl.getCar().getCarModel())
                    .cupCategory(lbl.getCar().getCupCategory())
                    .raceNumber(lbl.getCar().getRaceNumber())
                    .teamGuid(lbl.getCar().getTeamGuid())
                    .teamName(lbl.getCar().getTeamName())
                    .drivers(drivers)
                    .build();
            return LeaderBoardLine.builder().car(car).build();
        }).collect(Collectors.toList());
    }
}
