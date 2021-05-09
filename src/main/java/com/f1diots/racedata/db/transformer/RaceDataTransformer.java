package com.f1diots.racedata.db.transformer;

import com.f1diots.racedata.db.model.*;
import com.f1diots.racedata.task.model.Car;
import com.f1diots.racedata.task.model.RaceData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

            Map<Integer, AccDriver> drivers = new HashMap<>();
            int i = 0;
            for (com.f1diots.racedata.task.model.AccDriver d : lbl.getCar().getDrivers()) {
                AccDriver driver = AccDriver.builder()
                        .firstName(d.getFirstName())
                        .lastName(d.getLastName())
                        .playerId(d.getPlayerId())
                        .shortName(d.getShortName())
                        .build();
                drivers.putIfAbsent(i, driver);
            }

            List<Lap> carLaps = raceData.getLaps().stream()
                    .filter(lap -> carId.equals(lap.getCarId()))
                    .map(lap -> Lap.builder()
                            .lapTime(lap.getLaptime())
                            .validForBest(lap.getValidForBest())
                            .driver(drivers.get(lap.getDriverIndex().intValue()))
                            .splits(lap.getSplits())
                            .build())
                    .collect(Collectors.toList());
            com.f1diots.racedata.task.model.AccCar car = com.f1diots.racedata.task.model.AccCar.byId(lbl.getCar().getCarModel());
            return LeaderBoardLine.builder()
                    .sessionCarId(SessionCarId.builder().carId(carId.intValue()).sessionId(sessionId).build())
                    .laps(carLaps)
                    .carGuid(lbl.getCar().getCarGuid())
                    .carModel(AccCar.builder().id(car.getId()).carClass(car.getCarClass()).name(car.getName()).year(car.getYear()).build())
                    .cupCategory(lbl.getCar().getCupCategory())
                    .raceNumber(lbl.getCar().getRaceNumber())
                    .teamGuid(lbl.getCar().getTeamGuid())
                    .teamName(lbl.getCar().getTeamName())
                    .build();
        }).collect(Collectors.toList());
    }
}
