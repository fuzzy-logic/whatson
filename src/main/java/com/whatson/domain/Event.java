package com.whatson.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

/**
 * Represents a public Event occurring in city at time/location
 */
public class Event {

    public Event() {



    }

    private int id;
    private String title;
    private Date startDate;
    private Date endDate;

}
