package com.whatson.application;

import com.whatson.infrastructure.EventRepository;
import com.whatson.domain.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application event service implementing application logic
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<EventVO> getNext7DaysEvents(int page) {
        return eventRepository.getEventsXDaysAhead(7, page);
    }
}
