package com.sbbonline.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import javax.ejb.Singleton;
import java.io.Serializable;
import java.util.List;

@Log4j
@Getter
@Setter
@Singleton
public class TrainsDto  implements Serializable {

    private List<TrainDto> trains;

}
