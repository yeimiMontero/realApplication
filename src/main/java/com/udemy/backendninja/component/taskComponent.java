package com.udemy.backendninja.component;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("taskComponent")
public class taskComponent {

    private static final Log LOG = LogFactory.getLog(taskComponent.class);

    //Para indicarle a spring que es una tarea repetitiva
    @Scheduled(fixedDelay = 5000)
    public void doTask(){
        LOG.info("TIME IS: "+ new Date());
    }


}
