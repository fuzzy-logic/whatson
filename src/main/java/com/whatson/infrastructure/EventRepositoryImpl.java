package com.whatson.infrastructure;


import com.whatson.domain.CategoriesVO;
import com.whatson.domain.EventSearchResult;
import com.whatson.domain.EventVO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Fetches Events data from eventful http api
 *
 * http://api.eventful.com/docs/events/search
 */
@Service
public class EventRepositoryImpl implements EventRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepositoryImpl.class);

    private Set<String> categories = new HashSet<String>();

    @Value("${eventful.rooturl}")
    private String rootUrl = "http://api.eventful.com";

    @Value("${eventful.appkey}")
    private String appkey;

    @Autowired
    private Unmarshaller unmarshaller;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<EventVO> getEventsXDaysAhead(int numOfDays, int page) {
        String requestUrl = rootUrl + "/rest/events/search?" + params(page);
        LOGGER.trace("getEventsXDaysAhead(" + numOfDays + ") requestUrl=" + requestUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        EventSearchResult results = (EventSearchResult) unmarshaller.unmarshall(response.getBody());
        return results.getEvents();
    }

    @Override
    public List<CategoriesVO.Category> getCategories() {
        String requestUrl = rootUrl + "/rest/categories/list?&app_key=" + appkey;

        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        CategoriesVO results = (CategoriesVO) unmarshaller.unmarshall(response.getBody());

        return results.getCategories();
    }

    @Override
    public List<EventVO> getEventsByCategoryForXDayAhead(String category, int numOfDays, int page) {
        String requestUrl = rootUrl + "/rest/events/search?" + paramsForDate(category, page, numOfDays);
        LOGGER.trace("getEventsByCategoryForXDayAhead(" + numOfDays + ") requestUrl=" + requestUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        EventSearchResult results = (EventSearchResult) unmarshaller.unmarshall(response.getBody());
        return results.getEvents();
    }

    private String params(int page) {
        String params = "date=This Week&location=London&include=categories&page_size=20&page_number=" +
                page + "&sort_order=date&sort_direction=ascending&app_key=" + appkey;
        return params;
    }

    private String paramsForDate(String category, int page, int daysAhead) {


        DateTime dt = new DateTime();
        DateTime searchDate = dt.plusDays(daysAhead);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateFormat.format(searchDate.toDate());
        String dateRange = dateString + "-" + dateString;
        String params = "category=" + category + "&date=" + dateRange + "&location=London&include=categories&page_size=20&page_number=" +
                page + "&sort_order=date&sort_direction=ascending&app_key=" + appkey;
        return params;
    }






}


