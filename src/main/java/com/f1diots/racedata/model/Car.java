package com.f1diots.racedata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class  Car {
    private int carId;
    private int raceNumber;
    private AccCar carDetails;
    private int carModel;
    private int cupCategory; //TODO - map to enum?
    private String teamName;
    private int carGuid;
    private int teamGuid;
    private List<AccDriver> drivers;
}