package com.f1diots.racedata.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaderBoardLineTiming {
    private long lastLap;
    private long[] lastSplits;
    private long bestLap;
    private long[] bestSplits;
    private long totalTime;
    private long lapCount;
    private long lastSplitId;
}
