package com.whatson.infrastructure;


import com.whatson.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gawain on 21/10/2016.
 */
@Service
public class EventRepositoryImpl implements EventRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepositoryImpl.class);


    public EventRepositoryImpl() {
        this.template = new RestTemplate();
    }

    @Value("${eventful.rooturl}")
    private String rootUrl = "http://api.eventful.com";

    @Value("${eventful.appkey}")
    private String appkey;

    @Autowired
    private RestTemplate template;


    @Override
    public List<Event> getEventsXDaysAhead(int numOfDays) {
        String requestUrl = rootUrl + "/rest/events/search?" + params();
        LOGGER.trace("getEventsXDaysAhead() rootUrl=" + rootUrl);
        ResponseEntity<String> response = template.getForEntity(requestUrl, String.class);
        return new ArrayList<>();
    }

    private String params() {
        String params = "date=Today&location=London&app_key=" + appkey;
        return params;
    }

}


