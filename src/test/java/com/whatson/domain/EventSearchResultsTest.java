package com.whatson.domain;

import com.whatson.infrastructure.Unmarshaller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import test.utils.FileTools;

import java.io.InputStream;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventSearchResultsTest {

    @Autowired
    private Unmarshaller unmarshaller;

    @Test
    public void testCategoriesReturnInOrder() throws Exception {


        Resource resource = new ClassPathResource("events-search-categories-this-week-london.xml");
        EventSearchResult results = (EventSearchResult) unmarshaller.unmarshall(resource.getInputStream());

        System.out.println("****************************************************************************");
        System.out.println(results.getTotalItems());



    }
}
