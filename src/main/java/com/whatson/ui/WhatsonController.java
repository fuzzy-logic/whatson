package com.whatson.ui;

import com.whatson.application.EventService;
import com.whatson.infrastructure.EventRepository;
import com.whatson.infrastructure.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by gawain on 21/10/2016.
 */


@Controller
public class WhatsonController {

    @Autowired
    EventService eventService;

    @RequestMapping(value="/", method=RequestMethod.GET)
    @ResponseBody
    public String index() {
        return "Welcome to whatson!";
    }

    @RequestMapping(value="/events", method=RequestMethod.GET)
    public String events(Model model) {
        //List<EventVO> events = eventService.getNext7DaysEvents();
        model.addAttribute("title", "Whatson");
        return "events";
    }

}