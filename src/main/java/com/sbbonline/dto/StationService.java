package com.sbbonline.dto;

import com.sbbonline.util.Receiver;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@Getter
@Setter
@ManagedBean(name = "stationService", eager = true)
@ApplicationScoped
public class StationService {

    @EJB
    Receiver receiver;

    private List<StationDto> stationsDto;

    @PostConstruct
    public void init() {
        stationsDto = receiver.getStationsDto();
    }

    StationDto getStationDtoById(Long id) {
        for (StationDto stationDto :
                stationsDto) {
            if (stationDto.getStationId().equals(id))
                return stationDto;
        }
        return null;
    }


}
