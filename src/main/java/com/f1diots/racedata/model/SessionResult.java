package com.f1diots.racedata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionResult {
    @JsonProperty("bestlap")
    private Long bestLap;

    private Long[] bestSplits;
}