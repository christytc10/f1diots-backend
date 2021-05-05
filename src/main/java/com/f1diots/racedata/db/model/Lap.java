package com.f1diots.racedata.db.model;

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
    @ManyToOne(cascade = {CascadeType.MERGE})
    private AccDriver driver;
    private Long lapTime;
    private Boolean validForBest;
    @Convert(converter = LapSplitsConverter.class)
    private List<Long> splits;
}
