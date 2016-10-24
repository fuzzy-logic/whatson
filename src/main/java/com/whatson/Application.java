package com.whatson;

/**
 * Created by gawain on 21/10/2016.
 */

import com.whatson.infrastructure.CategoriesVO;
import com.whatson.infrastructure.EventSearchResult;
import com.whatson.infrastructure.EventVO;
import com.whatson.infrastructure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@EnableCaching
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepositoryImpl.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(EventSearchResult.class, EventVO.class, CategoriesVO.class);
        return jaxb2Marshaller;
    }

    @Bean
    public Unmarshaller<EventSearchResult> eventUnmarshaller() {
       EventUnmarshaller eventUnmarshaller = new EventUnmarshaller();
       return eventUnmarshaller;
    }



}