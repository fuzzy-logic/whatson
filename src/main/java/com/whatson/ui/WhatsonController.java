package com.whatson.ui;

import com.whatson.application.EventService;
import com.whatson.infrastructure.CategoriesVO;
import com.whatson.infrastructure.EventRepositoryImpl;
import com.whatson.infrastructure.EventVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Whatson event controller
 *
 *
 */


@Controller
public class WhatsonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepositoryImpl.class);

    @Autowired
    EventService eventService;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index() {
        return "redirect:/events.html";
    }

    @RequestMapping(value="/events", method=RequestMethod.GET)
    public String events(Model model,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "category", defaultValue = "music") String category) {
        LOGGER.trace("events() page=" + page);
        List<EventVO> events = eventService.getNext7DaysEvents(category, page);
        List<CategoriesVO.Category> categories = eventService.getCategories();
        model.addAttribute("events", events);
        model.addAttribute("pageNum", ++page);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", categories);
        return "events";
    }

}