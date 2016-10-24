package com.whatson.ui;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import test.utils.FileTools;



/**
 * Test Whatson Mvc Controller
 *
 * TODO:
 * 1.) test data in controller model and remove crude contains("") expectations - return ModalAndView from Controller
 * 2.) Replace rest template with HttpRequest bean that return InputSource to avoid multiple transforms of response
 * 3.) Add more than just happy path tests eg: null responses, timeoutes, etc..
 *
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
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/events.html"));
    }

    @Test
    public void getEvents() throws Exception {

        String responseBody = FileTools.openClasspathFile("events-search-today-london.xml");
        String category = "music";
        String params = "category=" + category + "&date=This%20Week&location=London&include=categories&page_size=20&page_number=1&sort_order=date&sort_direction=ascending";
        String expectedEventUrl = eventfulRootUrl + "/rest/events/search?" + params + "&app_key=" + appKey;

        String categoryXml = FileTools.openClasspathFile("categories.xml");
        String expectedCategoryUrl = eventfulRootUrl + "/rest/categories/list?app_key=" + appKey;

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(once(), requestTo(expectedEventUrl)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        server.expect(once(), requestTo(expectedCategoryUrl)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(categoryXml, MediaType.APPLICATION_JSON));


        // assert that titles of each event are contained in html response
        mvc.perform(MockMvcRequestBuilders.get("/events?category=" + category).accept(MediaType.TEXT_HTML))
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
                ))))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attributeExists("pageNum"))
                .andExpect(model().attributeExists("selectedCategory"))
                .andExpect(model().attributeExists("categories"));
    }

}
