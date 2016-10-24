package com.whatson.application;

import com.whatson.domain.CategoriesVO;
import com.whatson.infrastructure.EventRepository;
import com.whatson.domain.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application event service implementing application logic
 *
 * Use of this service conforms to DDD layers: UI, Application, Domain, Infrastructure
 *
 * This service not currently tested as it's  a simple one liner and my time is limited
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
