package com.t_systems.t_medical_center_system;

import lombok.extern.slf4j.Slf4j;


import javax.ejb.Startup;


import javax.faces.push.Push;

import javax.faces.push.PushContext;

import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;

@Named
@Slf4j
@Startup
public class WebSocketPush implements Serializable {
    @Inject
    @Push(channel = "websocket")
    private PushContext pushContext;


    public void sendMessage(Object message) {
        log.info("Pushing notification to jsf " + message);
        pushContext.send(message);
        log.info("Pushing successful");
    }

}