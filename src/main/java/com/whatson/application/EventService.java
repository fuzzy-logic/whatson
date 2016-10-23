package com.whatson.application;

import com.whatson.domain.EventVO;

import java.util.List;

/**
 * Event application service interface
 */
public interface EventService {

    List<EventVO> getNext7DaysEvents(int page);
}
