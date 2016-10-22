package com.whatson.infrastructure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by gawain on 22/10/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventUnmarshallerTest {

    @Autowired
    Unmarshaller<EventSearchResult> eventUnmarshaller;

    @Test
    public void unmarshallSampleEventfulXml() throws Exception {
        Resource resource = new ClassPathResource("sample-events-search.xml");
        String[] expectedIds = new String[] {"123", "234", "345"};
        String sampleEventfulXml = resource.getFile().toString();

        EventSearchResult results = eventUnmarshaller.unmarshall(sampleEventfulXml);
        List<EventVO> events = results.getEvents();
        List<String> eventIds = results.getEvents().stream().map(e -> e.getId()).collect(Collectors.toList());

        assertThat(results.getTotalItems(), is(equalTo(60)));
        assertThat(results.getPageSize(), is(equalTo(10)));
        assertThat(results.getPageCount(), is(equalTo(6)));
        assertThat(events, is(allOf(notNullValue(), instanceOf(ArrayList.class), hasSize(3))));
        assertThat(eventIds, contains(expectedIds));

    }
}
