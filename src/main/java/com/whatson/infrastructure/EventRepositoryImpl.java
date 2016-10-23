package com.whatson.infrastructure;


import com.whatson.domain.EventVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public List<EventVO> getEventsXDaysAhead(int numOfDays, int page) {
        String requestUrl = rootUrl + "/rest/events/search?" + params(page);
        LOGGER.trace("getEventsXDaysAhead(" + numOfDays + ") requestUrl=" + requestUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        EventSearchResult results = unmarshaller.unmarshall(response.getBody());
        return results.getEvents();
    }

    private String params(int page) {
        String params = "date=This Week&location=London&include=categories&page_size=20&page_number=" +
                page + "&sort_order=date&sort_direction=ascending&app_key=" + appkey;
        return params;
    }






}


