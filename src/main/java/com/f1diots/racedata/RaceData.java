package com.f1diots.racedata;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RaceData {
    private String id;
    private String trackName;
    private String sessionType;
}
