package com.whatson.application;

import com.whatson.infrastructure.CategoriesVO;
import com.whatson.infrastructure.EventRepository;
import com.whatson.infrastructure.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application event service implementing application logic
 *
 * TODO
 * 1.) Write test for this service (not done as it's  a simple one liner and my time is very limited)
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<EventVO> getNext7DaysEvents(String categoryId, int page) {
        return eventRepository.getEventsXDaysAhead(categoryId, 7, page);
    }

    @Override
    public List<CategoriesVO.Category> getCategories() {
        return eventRepository.getCategories();
    }
}
