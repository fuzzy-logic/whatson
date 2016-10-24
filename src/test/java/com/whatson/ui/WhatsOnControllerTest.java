package com.whatson.ui;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.whatson.infrastructure.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import test.utils.FileTools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Test Whatson Mvc Controller
 */



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WhatsOnControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${eventful.rooturl}")
    String eventfulRootUrl;

    @Value("${eventful.appkey}")
    String appKey;

    @Test
    public void getIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Welcome to whatson!")));
    }

    @Test
    public void getEvents() throws Exception {

        String responseBody = FileTools.openClasspathFile("events-search-today-london.xml");

        String params = "date=This%20Week&location=London&include=categories&page_size=20&page_number=1&sort_order=date&sort_direction=ascending";
        String expectedUrl = eventfulRootUrl + "/rest/events/search?" + params + "&app_key=" + appKey;

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(once(), requestTo(expectedUrl)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        // assert that titles of each event are contained in html response
        mvc.perform(MockMvcRequestBuilders.get("/events").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(is(allOf(
                        containsString("<h1>Whatson: London</h1>"),
                        containsString(">Walking Holiday Spain<"),
                        containsString(">Turkish Pop-Up @ Arlo &amp; Moe #hithergreen</div>"),
                        containsString(">Orbis Access Benchmark Rally<"),
                        containsString(">London Food Tech Week<"),
                        containsString(">CANCELLED - Advanced Diamonds - WWR SC<"),
                        containsString(">Boom! - West Coast Special<"),
                        containsString(">Introducing Recreate DJ Shadow Endtroducing<"),
                        containsString(">SLAM - Spirit of Leadership<"),
                        containsString(">Practitioner Training Workshops<"),
                        containsString(">Libra&#39;s Birthday Bash<")
                ))));

    }





}
