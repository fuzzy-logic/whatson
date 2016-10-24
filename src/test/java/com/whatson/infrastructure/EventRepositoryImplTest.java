package com.whatson.infrastructure;

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

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertThat;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static  org.springframework.test.web.client.ExpectedCount.*;

/**
 * TODO
 *  1.) Replace rest template with HttpRequest bean that return InputSource to avoid multiple transforms of response
 *  2.) Add more than just happy path tests eg: null responses, timeoutes, etc..
 *  3.) RestTemplate expectatiosn are set in controller & repository
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
    public void getEventsThisWeekByCategory() throws Exception {

        String responseBody = FileTools.openClasspathFile("events-search-music-this-week.xml");
        String category = "music";
        String params = "category=" + category + "&date=This%20Week&location=London&include=categories&page_size=20&page_number=0&sort_order=date&sort_direction=ascending";
        String expectedUrl = eventfulRootUrl + "/rest/events/search?" + params + "&app_key=" + appKey;
        // Event IDs from  events-search-music-this-week.xml
        String[] expectedIds = new String[] {"E0-001-094140110-7", "E0-001-094140096-8", "E0-001-094275201-7",
                "E0-001-096113385-1", "E0-001-096099938-4", "E0-001-097136508-8", "E0-001-096114618-3",
                "E0-001-090660787-0", "E0-001-096940875-1", "E0-001-095323702-8"};

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(once(), requestTo(expectedUrl)).andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        List<EventVO> events = eventsRepository.getEventsXDaysAhead(category, 1, 0);

        List<String> eventIds = events.stream().map(e -> e.getId()).collect(Collectors.toList());

        assertThat(events, is(allOf(notNullValue(), instanceOf(ArrayList.class))));
        assertThat(events.size(), is(equalTo(10)));

        assertThat(eventIds, is(allOf(notNullValue(), containsInAnyOrder(expectedIds))));

    }


    @Test
    public void getCategories() throws Exception {

        String categoryResponseBody = FileTools.openClasspathFile("categories.xml");

        // Category ID's in categories.xml
        String[] expectedIds = new String[]{"music", "conference", "comedy" , "learning_education", "family_fun_kids", "festivals_parades",
                "movies_film", "food", "fundraisers", "art", "support", "holiday", "books", "attractions", "community",
                "business", "singles_social", "schools_alumni", "clubs_associations", "outdoors_recreation", "performing_arts",
                "animals", "politics_activism", "sales", "science", "religion_spirituality", "sports", "technology", "other"};


        String expectedCategoryUrl = eventfulRootUrl + "/rest/categories/list?app_key=" + appKey;

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(once(), requestTo(expectedCategoryUrl)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(categoryResponseBody, MediaType.APPLICATION_JSON));


       List<CategoriesVO.Category> categories = eventsRepository.getCategories();


        assertThat(categories.size(), is(equalTo(29)));

        List<String> categoryIds = categories.stream().map(c -> c.getId()).collect(Collectors.toList());

        assertThat(categoryIds, contains(expectedIds));



    }

}


