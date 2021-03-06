package com.whatson.infrastructure;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Fetches Events data from eventful http api
 *
 * http://api.eventful.com/docs/events/search
 *
 */
@Service
public class EventRepositoryImpl implements EventRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepositoryImpl.class);

    @Value("${eventful.rooturl}")
    private String rootUrl = "http://api.eventful.com";

    @Value("${eventful.appkey}")
    private String appkey;

    @Autowired
    private Unmarshaller unmarshaller;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Cacheable("events")
    public List<EventVO> getEventsXDaysAhead(String categoryId, int numOfDays, int page) {
        String requestUrl = rootUrl + "/rest/events/search?" + params(categoryId, page);
        LOGGER.trace("getEventsXDaysAhead(" + numOfDays + ") requestUrl=" + requestUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        EventSearchResult results = (EventSearchResult) unmarshaller.unmarshall(response.getBody());
        return results.getEvents();
    }

    @Override
    @Cacheable("categories")
    public List<CategoriesVO.Category> getCategories() {
        String requestUrl = rootUrl + "/rest/categories/list?&app_key=" + appkey;
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        CategoriesVO results = (CategoriesVO) unmarshaller.unmarshall(response.getBody());
        return results.getCategories();
    }

    private String params(String categoryId, int page) {
        String params = "category=" + categoryId + "&date=This Week&location=London&include=categories&page_size=20&page_number=" +
                page + "&sort_order=date&sort_direction=ascending&app_key=" + appkey;
        return params;
    }

}


