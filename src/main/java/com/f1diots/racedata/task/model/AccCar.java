package com.f1diots.racedata.task.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonSerialize(using = AccCarSerializer.class)
@JsonDeserialize(using = AccCarDeserializer.class)
public enum AccCar {
    //TODO - fix these enum names properly
    PORSCHE_991_GT3R("Porsche 991 GT3 R",2018,"GT3",0),
    MERCEDES_AMG_GT3("Mercedes-AMG GT3",2015,"GT3",1),
    FERRARI_488_GT3("Ferrari 488 GT3",2018,"GT3",2),
    AUDI_R8_LMS("Audi R8 LMS",2015,"GT3",3),
    LAMBORGHINI_HURACAN_GT3("Lamborghini Huracan GT3",2015,"GT3",4),
    MCLAREN_650S_GT3("McLaren 650S GT3",2015,"GT3",5),
    NISSAN_GT_R_NISMO_GT3("Nissan GT-R Nismo GT3",2018,"GT3",6),
    BMW_M6_GT3("BMW M6 GT3",2017,"GT3",7),
    BENTLEY_CONTINENTAL_GT3("Bentley Continental GT3",2018,"GT3",8),
    PORSCHE_991_II_GT3_CUP("Porsche 991 II GT3 Cup",2017,"GT3",9),
    NISSAN_GT_R_NISMO_GT3_2015("Nissan GT-R Nismo GT3",2015,"GT3",10),
    BENTLEY_CONTINENTAL_GT3_2015("Bentley Continental GT3",2015,"GT3",11),
    AMR_V12_VANTAGE_GT3("AMR V12 Vantage GT3",2013,"GT3",12),
    REITER_ENGINEERING_R_EX_GT3("Reiter Engineering R-EX GT3",2017,"GT3",13),
    EMIL_FREY_JAGUAR_G3("Emil Frey Jaguar G3",2012,"GT3",14),
    LEXUS_RC_F_GT3("Lexus RC F GT3",2016,"GT3",15),
    LAMBORGHINI_HURACAN_GT3_EVO("Lamborghini Huracan GT3 Evo",2019,"GT3",16),
    HONDA_NSX_GT3("Honda NSX GT3",2017,"GT3",17),
    LAMBORGHINI_HURACAN_SUPERTROFEO("Lamborghini Huracan SuperTrofeo",2015,"GT3",18),
    AUDI_R8_LMS_EVO("Audi R8 LMS Evo",2019,"GT3",19),
    AMR_V8_VANTAGE("AMR V8 Vantage",2019,"GT3",20),
    HONDA_NSX_GT3_EVO("Honda NSX GT3 Evo",2019,"GT3",21),
    MCLAREN_720S_GT3("McLaren 720S GT3",2019,"GT3",22),
    PORSCHE_911_II_GT3_R("Porsche 911 II GT3 R",2019,"GT3",23),
    FERRARI_488_GT3_EVO("Ferrari 488 GT3 Evo",2020,"GT3",24),
    MERCEDES_AMG_GT3_2020("Mercedes-AMG GT3",2020,"GT3",25),
    ALPINE_A110_GT4("Alpine A110 GT4",2018,"GT4",50),
    ASTON_MARTIN_VANTAGE_GT4("Aston Martin Vantage GT4",2018,"GT4",51),
    AUDI_R8_LMS_GT4("Audi R8 LMS GT4",2018,"GT4",52),
    BMW_M4_GT4("BMW M4 GT4",2018,"GT4",53),
    CAR_FIFTY_FOUR("Car 54 Where are you?",1961,"GT4",54),
    CHEVROLET_CAMARO_GT4("Chevrolet Camaro GT4",2017,"GT4",55),
    GINETTA_G55_GT4("Ginetta G55 GT4",2012,"GT4",56),
    KTM_X_BOW_GT4("KTM X-Bow GT4",2016,"GT4",57),
    MASERATI_MC_GT4("Maserati MC GT4",2016,"GT4",58),
    MCLAREN_570S_GT4("McLaren 570S GT4",2016,"GT4",59),
    MERCEDES_AMG_GT4("Mercedes AMG GT4",2016,"GT4",60),
    PORSCHE_718_CAYMAN_GT4_CLUBSPORT("Porsche 718 Cayman GT4 Clubsport",2019,"GT4",61);

    private String name;
    private int year;
    private String carClass;
    private int id;

    AccCar(String name, int year, String carClass, int id) {
        this.name = name;
        this.year = year;
        this.carClass = carClass;
        this.id = id;
    }

    public static AccCar byId(int abbr){
        for(AccCar v : values()){
            if( v.id == abbr){
                return v;
            }
        }
        return CAR_FIFTY_FOUR;
    }
}
