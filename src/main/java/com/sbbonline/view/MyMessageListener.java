package com.sbbonline.view;

import com.sbbonline.dto.TrainDto;
import com.sbbonline.dto.TrainsDto;
import com.sbbonline.util.Receiver;
import lombok.extern.log4j.Log4j;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.Serializable;

@Log4j
@SessionScoped
@ManagedBean(name = "SbbListener")
public class MyMessageListener implements MessageListener, Serializable {

    @EJB
    private Receiver receiver;

    public MyMessageListener(Receiver receiver) {
        this.receiver=receiver;
        log.info("Listener receiver created");
    }

    public MyMessageListener() {
        log.info("Listener default created");
    }

    @Override
    public void onMessage(Message message)  {
        log.info("Message received");
        receiver.getTimetable("Geneva%20Airport"); //from JMS server
    }

    public TrainsDto getTrainsDto() {
        return receiver.getTrainsDto();
    }

    public void setTrainsDto(TrainsDto trainsDto) {
        receiver.setTrainsDto(trainsDto);
    }

    public TrainDto getTrainDto() {
        return receiver.getTrainDto();
    }

    public void setTrainDto(TrainDto trainDto) {
        receiver.setTrainDto(trainDto);
    }

}
