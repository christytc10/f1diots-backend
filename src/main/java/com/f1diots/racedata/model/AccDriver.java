package com.f1diots.racedata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccDriver {
    private String firstName;
    private String lastName;
    private String shortName;
    private String playerId;
}
