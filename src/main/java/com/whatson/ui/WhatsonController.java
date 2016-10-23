package com.whatson.ui;

import com.whatson.application.EventService;
import com.whatson.infrastructure.EventRepositoryImpl;
import com.whatson.domain.EventVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by gawain on 21/10/2016.
 */


@Controller
public class WhatsonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepositoryImpl.class);

    @Autowired
    EventService eventService;

    @RequestMapping(value="/", method=RequestMethod.GET)
    @ResponseBody
    public String index() {
        return "Welcome to whatson!";
    }

    @RequestMapping(value="/events", method=RequestMethod.GET)
    public String events(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        LOGGER.trace("events() page=" + page);
        List<EventVO> events = eventService.getNext7DaysEvents(page);
        //LOGGER.trace("events() events=" + events);
        model.addAttribute("title", "Whatson");
        model.addAttribute("events", events);
        model.addAttribute("pageNum", ++page);
        return "events";
    }

}