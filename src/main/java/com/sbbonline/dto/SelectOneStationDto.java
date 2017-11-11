package com.sbbonline.dto;


import com.sbbonline.util.Receiver;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
@Getter
@Setter
public class SelectOneStationDto {

    @EJB
    private Receiver receiver;

    private String chosenStationDto;
    private StationDto stationDto;
    private List<StationDto> stationsDto;

    @ManagedProperty("#{stationService}")
    private StationService stationService;

    @PostConstruct
    public void init() {
        stationsDto = stationService.getStationsDto();
    }

    public void handleEvent() {
        receiver.getTimetable(stationDto.getStationName());
    }
}
