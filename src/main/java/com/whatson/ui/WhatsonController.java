package com.whatson.ui;

import com.whatson.infrastructure.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gawain on 21/10/2016.
 */


@RestController
public class WhatsonController {

    @Autowired
    EventRepository eventsRepository;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index() {
        return "Welcome to whatson!";
    }

}