package com.t_systems.t_medical_center_system;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import lombok.extern.slf4j.Slf4j;


import javax.enterprise.context.ApplicationScoped;

import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Slf4j
@Named
@ApplicationScoped
public class EventService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String URL_TO_GET_OCCASION_BY_REST = "http://localhost:8080/infoboard/occasion";
    private Client client = new Client();


    public List<EventDto> getEventsToday() {
        WebResource webResource = client.resource(URL_TO_GET_OCCASION_BY_REST);
        String response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class).getEntity(String.class);
        List<EventDto> list = null;
        try {
            list = objectMapper.readValue(response, new TypeReference<List<EventDto>>() {
            });
        } catch (IOException e) {
            log.error("error,can't load events " + e.getMessage());
        }
        return list;
    }

}
