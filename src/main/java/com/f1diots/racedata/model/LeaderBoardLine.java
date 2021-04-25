package com.f1diots.racedata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaderBoardLine {
    private Car car;
    private AccDriver currentDriver;
    private int currentDriverIndex;
    private LeaderBoardLineTiming timing;
    private long missingMandatoryPitstop;
    //private List<DriverTime> driverTotalTimes;
}
