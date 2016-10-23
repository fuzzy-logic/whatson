package com.whatson.infrastructure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        String[] expectedIds = new String[] {"123", "234", "345"};
        String sampleEventfulXml = openClasspathFile("sample-events-search.xml");

        //System.out.println("sampleEventfulXml: " +  sampleEventfulXml);

        EventSearchResult results = eventUnmarshaller.unmarshall(sampleEventfulXml);
        List<EventVO> events = results.getEvents();
        List<String> eventIds = results.getEvents().stream().map(e -> e.getId()).collect(Collectors.toList());

        assertThat(results.getTotalItems(), is(equalTo(60)));
        assertThat(results.getPageSize(), is(equalTo(10)));
        assertThat(results.getPageCount(), is(equalTo(6)));
        assertThat(events, is(allOf(notNullValue(), instanceOf(ArrayList.class), hasSize(3))));
        assertThat(eventIds, contains(expectedIds));

    }


    private String openClasspathFile(String filename) {

        Resource resource = new ClassPathResource(filename);
        String content = "";

        try{
            InputStream is = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                content += line + '\n';
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return content;
    }
}
