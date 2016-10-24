package com.whatson.infrastructure;

import com.whatson.domain.CategoriesVO;
import com.whatson.domain.EventVO;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
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

import java.text.SimpleDateFormat;
import java.time.LocalTime;
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

    @Test
    public void getEventsByCategory() throws Exception {

        String responseBody = FileTools.openClasspathFile("events-search-today-business-london.xml");
        int daysAhead = 0;

        DateTime dt = new DateTime();
        DateTime searchDate = dt.plusDays(daysAhead);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateFormat.format(searchDate.toDate());
        String dateRange = dateString + "-" + dateString;

        String params = "category=business&date=" + dateRange + "&location=London&include=categories&page_size=20&page_number=1&sort_order=date&sort_direction=ascending";
        String expectedUrl = eventfulRootUrl + "/rest/events/search?" + params + "&app_key=" + appKey;

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(once(), requestTo(expectedUrl)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        List<EventVO> events = eventsRepository.getEventsByCategoryForXDayAhead("business", 0, 1);



        assertThat(events, is(allOf(notNullValue(), instanceOf(ArrayList.class))));
        assertThat(events.size(), is(equalTo(20)));


    }

    @Test
    public void getCategories() throws Exception {

        String[] expectedIds = new String[]{"music", "conference", "comedy" , "learning_education", "family_fun_kids", "festivals_parades",
                "movies_film", "food", "fundraisers", "art", "support", "holiday", "books", "attractions", "community",
                "business", "singles_social", "schools_alumni", "clubs_associations", "outdoors_recreation", "performing_arts",
                "animals", "politics_activism", "sales", "science", "religion_spirituality", "sports", "technology", "other"};

        String responseBody = FileTools.openClasspathFile("categories.xml");

        String expectedUrl = eventfulRootUrl + "/rest/categories/list?app_key=" + appKey;

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(once(), requestTo(expectedUrl)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));



       List<CategoriesVO.Category> categories = eventsRepository.getCategories();


        assertThat(categories.size(), is(equalTo(29)));

        List<String> categoryIds = categories.stream().map(c -> c.getId()).collect(Collectors.toList());

        assertThat(categoryIds, contains(expectedIds));



    }

}


