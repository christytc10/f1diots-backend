package com.f1diots.racedata.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Container(containerName = "raceDataCollection")
public class RaceData {
    @Id
    private String id;
    private Instant timestamp;

    private String trackName;
    private String sessionType;
    private SessionResult sessionResult;
    private List<Lap> laps;
    //penalties
    //post-race penalties
}
