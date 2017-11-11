package com.sbbonline.util;

import com.sbbonline.dto.*;
import com.sbbonline.view.MyMessageListener;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import lombok.extern.log4j.Log4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

@Log4j
@Startup
@Singleton
public class Receiver {

    private QueueConnection connection;
    private QueueSession session;
    private QueueReceiver receiver;

    @EJB
    private TrainsDto trainsDto;




    @EJB
    private TrainDto trainDto;

    public Receiver() {
        log.info("receiver created");
    }

    @PostConstruct
    public void receive() {

        Hashtable<String, String> props = new Hashtable<>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://localhost:61616");
        props.put("queue.js-queue", "sbb_queue");
        props.put("connectionFactoryNames", "queueCF");

        try {
            Context context = new InitialContext(props);
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup("queueCF");
            Queue queue = (Queue) context.lookup("js-queue");

            connection = connectionFactory.createQueueConnection();
            connection.start();

            session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

            receiver = session.createReceiver(queue);

            receiver.setMessageListener(new MyMessageListener(this));
        } catch (NamingException e) {
            log.error(e.toString());
        } catch (JMSException e) {
            log.error(e.toString());
        }

        getTimetable("Geneva%20Airport");
    }

    public void getTimetable(String stationName) {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource("http://localhost:8082/board/"+stationName);
        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        TrainDto [] res = response.getEntity(TrainDto[].class);
        List<TrainDto> trainsDtoList = new ArrayList<TrainDto>(Arrays.asList(res));
        trainsDto.setTrains(trainsDtoList);
    }

    public List <StationDto> getStationsDto() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource("http://localhost:8082/stations");
        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        StationDto[] res = response.getEntity(StationDto[].class);
        List<StationDto> stationsDto = new ArrayList<StationDto>(Arrays.asList(res));
        return stationsDto;
    }


    @PreDestroy
    public void destroyReceiver() {

        try {
            receiver.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            log.info(e.toString());
        }

    }

    public TrainsDto getTrainsDto() {
        return trainsDto;
    }

    public void setTrainsDto(TrainsDto trainsDto) {
        this.trainsDto= trainsDto;
    }

    public TrainDto getTrainDto() {
        return trainDto;
    }

    public void setTrainDto(TrainDto trainDto) {
        this.trainDto= trainDto;
    }

}
