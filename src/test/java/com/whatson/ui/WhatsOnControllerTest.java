package com.whatson.ui;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
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
    public void getEevnts() throws Exception {

        String responseBody = FileTools.openClasspathFile("events-search-today-london.xml");
        String expectedHtmlTitle = "<h1>Welcome to Whatson</h1>";

        String expectedUrl = eventfulRootUrl + "/rest/events/search?date=Today&location=London&app_key=" + appKey;

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(once(), requestTo(expectedUrl)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        mvc.perform(MockMvcRequestBuilders.get("/events").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedHtmlTitle)))
                // Assert expected event id & title from test/resources/events-search-today-london.xml:
                .andExpect(content().string(containsString("<div id=\"event-id\">E0-001-097240868-1</div>")))
                .andExpect(content().string(containsString("<div id=\"event-title\">Walking Holiday Spain</div>")))

                .andExpect(content().string(containsString("<div id=\"event-id\">E0-001-096749091-6</div>")))
                .andExpect(content().string(containsString("<div id=\"event-title\">Turkish Pop-Up @ Arlo &amp; Moe #hithergreen</div>")))

                .andExpect(content().string(containsString("<div id=\"event-id\">E0-001-097158315-2</div>")))
                .andExpect(content().string(containsString("<div id=\"event-title\">Orbis Access Benchmark Rally</div>")))

                .andExpect(content().string(containsString("<div id=\"event-id\">E0-001-097158263-0</div>")))
                .andExpect(content().string(containsString("<div id=\"event-title\">London Food Tech Week</div>")))

                .andExpect(content().string(containsString("<div id=\"event-id\">E0-001-096840188-5</div>")))
                .andExpect(content().string(containsString("<div id=\"event-title\">CANCELLED - Advanced Diamonds - WWR SC</div>")))

                .andExpect(content().string(containsString("<div id=\"event-id\">E0-001-096964296-6</div>")))
                .andExpect(content().string(containsString("<div id=\"event-title\">Boom! - West Coast Special</div>")))

                .andExpect(content().string(containsString("<div id=\"event-id\">E0-001-096249580-0</div>")))
                .andExpect(content().string(containsString("<div id=\"event-title\">Introducing Recreate DJ Shadow Endtroducing</div>")))

                .andExpect(content().string(containsString("<div id=\"event-id\">E0-001-092943812-7@2016102118</div>")))
                .andExpect(content().string(containsString("<div id=\"event-title\">SLAM - Spirit of Leadership</div>")))

                .andExpect(content().string(containsString("<div id=\"event-id\">E0-001-096268380-3@2016102110</div>")))
                .andExpect(content().string(containsString("<div id=\"event-title\">Practitioner Training Workshops</div>")))

                .andExpect(content().string(containsString("<div id=\"event-id\">E0-001-096652742-6@2016102122</div>")))
                .andExpect(content().string(containsString("<div id=\"event-title\">Libra&#39;s Birthday Bash</div>"))) ;
    }





}
