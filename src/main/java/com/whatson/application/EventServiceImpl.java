package com.whatson.application;

import com.whatson.application.EventService;
import com.whatson.infrastructure.EventRepository;
import com.whatson.infrastructure.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gawain on 22/10/2016.
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<EventVO> getNext7DaysEvents() {
        return eventRepository.getEventsXDaysAhead(7);
    }
}
