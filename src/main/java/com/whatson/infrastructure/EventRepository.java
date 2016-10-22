package com.whatson.infrastructure;

import com.whatson.domain.Event;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Retreive events from http://api.eventful.com
 */
public interface EventRepository {

     public List<Event> getEventsXDaysAhead(int numOfDays);

}
