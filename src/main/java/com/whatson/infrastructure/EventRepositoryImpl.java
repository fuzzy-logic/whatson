package com.whatson.infrastructure;


import com.whatson.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fetches Events data from eventful http api
 *
 * http://api.eventful.com/docs/events/search
 */
@Service
public class EventRepositoryImpl implements EventRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepositoryImpl.class);


    @Value("${eventful.rooturl}")
    private String rootUrl = "http://api.eventful.com";

    @Value("${eventful.appkey}")
    private String appkey;

    @Autowired
    private Unmarshaller<EventSearchResult> unmarshaller;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<EventVO> getEventsXDaysAhead(int numOfDays) {
        String requestUrl = rootUrl + "/rest/events/search?" + params();
        LOGGER.trace("getEventsXDaysAhead() requestUrl=" + requestUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        EventSearchResult results = unmarshaller.unmarshall(response.getBody());
        return results.getEvents();
    }

    private String params() {
        String params = "date=Today&location=London&app_key=" + appkey;
        return params;
    }






}


