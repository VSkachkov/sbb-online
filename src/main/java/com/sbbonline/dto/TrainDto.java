package com.sbbonline.dto;

import lombok.Getter;
import lombok.Setter;

import javax.ejb.Singleton;
import java.sql.Time;

@Getter
@Setter
@Singleton
public class TrainDto {

    private Long trainId;
    private String trainName;
    private boolean departMon;
    private boolean departTue;
    private boolean departWed;
    private boolean departThu;
    private boolean departFri;
    private boolean departSat;
    private boolean departSun;
    private Long capacity;
    private String initStation;
    private String lastStation;
    private Time departureTime;
    private Time arrivalTime;
    private Long trainTypeNumber;
    public Long trainType;
    public Long trainTypeDtos;
    public Long carriage;
    private String status;



    public TrainDto(Long trainId, String trainName, boolean departMon,
                    boolean departTue, boolean departWed, boolean departThu,
                    boolean departFri, boolean departSat, boolean departSun,
                    Long capacity, String initStation, String lastStation,
                    Time departureTime, Time arrivalTime) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.departMon = departMon;
        this.departTue = departTue;
        this.departWed = departWed;
        this.departThu = departThu;
        this.departFri = departFri;
        this.departSat = departSat;
        this.departSun = departSun;
        this.capacity = capacity;
        this.initStation = initStation;
        this.lastStation = lastStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public TrainDto() {
    }


}
