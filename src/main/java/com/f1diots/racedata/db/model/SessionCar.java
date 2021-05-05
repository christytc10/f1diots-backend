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
@Table(name = "session_car")
public class SessionCar {
    @EmbeddedId
    private SessionCarId sessionCarId;
    private int raceNumber;
    private int carModel;
    private int cupCategory;
    private String teamName;
    private int carGuid;
    private int teamGuid;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Lap> laps;
}
