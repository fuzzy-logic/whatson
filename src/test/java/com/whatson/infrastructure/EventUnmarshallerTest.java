package com.whatson.infrastructure;

import com.whatson.domain.CategoriesVO;
import com.whatson.domain.EventSearchResult;
import com.whatson.domain.EventVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.utils.FileTools;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test XML unmarshalling
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventUnmarshallerTest {

    @Autowired
    Unmarshaller eventUnmarshaller;



    @Test
    public void unmarshallSampleEventfulXml() throws Exception {
        String[] expectedIds = new String[] {"123", "234", "345"};
        String sampleEventfulXml = FileTools.openClasspathFile("sample-events-search.xml");

        EventSearchResult results = (EventSearchResult) eventUnmarshaller.unmarshall(sampleEventfulXml);
        List<EventVO> events = results.getEvents();
        List<String> eventIds = results.getEvents().stream().map(e -> e.getId()).collect(Collectors.toList());

        assertThat(results.getTotalItems(), is(equalTo(60)));
        assertThat(results.getPageSize(), is(equalTo(10)));
        assertThat(results.getPageCount(), is(equalTo(6)));
        assertThat(events, is(allOf(notNullValue(), instanceOf(ArrayList.class), hasSize(3))));
        assertThat(eventIds, contains(expectedIds));

        for (EventVO event : events) {
            System.out.println(event);
        }

    }

    @Test
    public void unmarshallSingleEventfulXml() throws Exception {
        String[] expectedIds = new String[] {"ABC"};
        String sampleEventfulXml = FileTools.openClasspathFile("single-event-search.xml");

        EventSearchResult results = (EventSearchResult) eventUnmarshaller.unmarshall(sampleEventfulXml);
        List<EventVO> events = results.getEvents();

        assertThat(results.getTotalItems(), is(equalTo(1)));
        assertThat(results.getPageSize(), is(equalTo(10)));
        assertThat(results.getPageCount(), is(equalTo(1)));
        assertThat(events, is(allOf(notNullValue(), instanceOf(ArrayList.class), hasSize(1))));

        for (EventVO event : events) {
            assertThat(event.getId(), is(equalTo("ABC")));
            assertThat(event.getTitle(), is(equalTo("Walking Holiday Spain")));
            assertThat(event.getStartDateString(), is(equalTo("2016-10-21 13:00:00")));
            assertThat(event.getEndDateString(), is(equalTo("2016-10-21 16:00:00")));
            assertThat(event.getUrl(), is(equalTo("http://london.eventful.com/events/walking-holiday-spain-/E0-001-097240868-1")));
            assertThat(event.getDescription(), is(equalTo("Andalusia would be perfect for you to enjoy most memorable moments of your life.")));
            assertThat(event.getImageUrl(), is(equalTo("http://s4.evcdn.com/images/small/I0-001/034/270/143-7.jpeg_/walking-holiday-spain-43.jpeg")));

        }

    }

    @Test
    public void unmarshallCategories() throws Exception {
        String[] expectedIds = new String[]{"music", "conference", "comedy" , "learning_education", "family_fun_kids", "festivals_parades",
                "movies_film", "food", "fundraisers", "art", "support", "holiday", "books", "attractions", "community",
                "business", "singles_social", "schools_alumni", "clubs_associations", "outdoors_recreation", "performing_arts",
                "animals", "politics_activism", "sales", "science", "religion_spirituality", "sports", "technology", "other"};
        String categoriesXml = FileTools.openClasspathFile("categories.xml");

        CategoriesVO categories = (CategoriesVO) eventUnmarshaller.unmarshall(categoriesXml);

        List<String> categoryIds = categories.getCategories().stream().map(c -> c.getId()).collect(Collectors.toList());

        assertThat(categoryIds, contains(expectedIds));

    }

}
