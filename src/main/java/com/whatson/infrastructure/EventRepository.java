package com.whatson.infrastructure;

import com.whatson.domain.EventVO;

import java.util.List;

/**
 * Retreive events from http://api.eventful.com
 */
public interface EventRepository {

     public List<EventVO> getEventsXDaysAhead(int numOfDays, int page);

}
