package com.whatson.infrastructure;

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
    Unmarshaller<EventSearchResult> eventUnmarshaller;

    @Test
    public void unmarshallSampleEventfulXml() throws Exception {
        String[] expectedIds = new String[] {"123", "234", "345"};
        String sampleEventfulXml = FileTools.openClasspathFile("sample-events-search.xml");

        EventSearchResult results = eventUnmarshaller.unmarshall(sampleEventfulXml);
        List<EventVO> events = results.getEvents();
        List<String> eventIds = results.getEvents().stream().map(e -> e.getId()).collect(Collectors.toList());

        assertThat(results.getTotalItems(), is(equalTo(60)));
        assertThat(results.getPageSize(), is(equalTo(10)));
        assertThat(results.getPageCount(), is(equalTo(6)));
        assertThat(events, is(allOf(notNullValue(), instanceOf(ArrayList.class), hasSize(3))));
        assertThat(eventIds, contains(expectedIds));

        for (EventVO event : events) {
            System.out.println(event + " <img src=\"" + event.getImage().getUrl() + "\"/>");
        }

    }

}
