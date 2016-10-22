package com.whatson.infrastructure;

import com.whatson.domain.Event;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertThat;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static  org.springframework.test.web.client.ExpectedCount.*;

/**
 * Created by gawain on 21/10/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventRepositoryImplTest {

    @Autowired
    private EventRepository eventsRepository;

    @Autowired
    RestTemplate restTemplate;

    @Value("${eventful.rooturl}")
    String eventfulRootUrl;

    @Value("${eventful.appkey}")
    String appKey;

    @Test
    public void getNext1DayEvents() throws Exception {


        Resource resource = new ClassPathResource("events-search-today-london.xml");
        String responseBody = resource.getFile().toString();

        String pathAndParams = "/rest/events/search?date=Today&location=London&app_key=" + appKey;

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(once(), requestTo(eventfulRootUrl + pathAndParams)).andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        List<Event> events = eventsRepository.getEventsXDaysAhead(1);

        assertThat(events, is(allOf(notNullValue(), instanceOf(ArrayList.class))));
        assertThat(events.size(), is(equalTo(0)));
        //assertThat(events, is(allOf(notNullValue(), containsInAnyOrder(new ArrayList()))));
       // assertThat(response.getBody(), is(allOf(notNullValue(), instanceOf(String.class), equalTo(jsonBody))));

    }

}


