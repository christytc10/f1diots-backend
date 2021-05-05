package com.f1diots.racedata.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lap {
    private Long carId;
    private Long driverIndex;
    private Long laptime;
    @JsonProperty("isValidForBest")
    private Boolean validForBest;
    private List<Long> splits;
}
