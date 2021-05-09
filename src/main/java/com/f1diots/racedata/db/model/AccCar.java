package com.f1diots.racedata.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "car")
public class AccCar {

    @Id
    private int id;

    private String name;
    private int year;
    @Column(name = "car_class")
    private String carClass;
}