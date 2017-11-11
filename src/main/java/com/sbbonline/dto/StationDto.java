package com.sbbonline.dto;

import com.sbbonline.util.Receiver;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;


@ManagedBean(name = "stationDto", eager = true)
@SessionScoped
@Getter
@Setter
public class StationDto implements Serializable {

    @EJB
    private Receiver receiver;

    private static final long serialVersionUID = 1L;
    private Long stationId;
    private Long cantonId;
    private String stationName;
    private String cantonName;

    public StationDto() {

    }

    public StationDto(Long stationId, String stationName, String cantonName) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.cantonName = cantonName;
    }

    @Override
    public String toString() {
        return stationName;
    }
}
