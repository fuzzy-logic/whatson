package com.whatson.infrastructure;

import com.whatson.domain.EventVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import test.utils.FileTools;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        String responseBody = FileTools.openClasspathFile("events-search-today-london.xml");

        String params = "date=This%20Week&location=London&include=categories&page_size=20&page_number=0&sort_order=date&sort_direction=ascending";
        String expectedUrl = eventfulRootUrl + "/rest/events/search?" + params + "&app_key=" + appKey;

        String[] expectedIds = new String[] {"E0-001-097240868-1", "E0-001-096749091-6", "E0-001-097158315-2",
                "E0-001-097158263-0", "E0-001-096840188-5", "E0-001-096964296-6", "E0-001-096249580-0",
                "E0-001-092943812-7@2016102118", "E0-001-096268380-3@2016102110", "E0-001-096652742-6@2016102122"};

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(once(), requestTo(expectedUrl)).andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        List<EventVO> events = eventsRepository.getEventsXDaysAhead(1, 0);

        List<String> eventIds = events.stream().map(e -> e.getId()).collect(Collectors.toList());

        assertThat(events, is(allOf(notNullValue(), instanceOf(ArrayList.class))));
        assertThat(events.size(), is(equalTo(10)));
        assertThat(eventIds, is(allOf(notNullValue(), containsInAnyOrder(expectedIds))));

    }

}


