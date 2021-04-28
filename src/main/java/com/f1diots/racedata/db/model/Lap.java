package com.f1diots.racedata.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "lap")
public class Lap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long carId;
    private Long driverIndex;
    private Long laptime;
    @JsonProperty("isValidForBest")
    private Boolean validForBest;
    @ManyToOne
    private RaceSession raceSession;


    //private List<Long> splits; //TODO - need a whole table for lap splits?
}
