package com.whatson.infrastructure;

import com.whatson.domain.CategoriesVO;
import com.whatson.domain.EventVO;

import java.util.List;
import java.util.Set;

/**
 * Retreive events from http://api.eventful.com
 */
public interface EventRepository {

     public List<EventVO> getEventsXDaysAhead(String categoryId, int numOfDays, int page);

     //public List<EventVO> getEventsByCategoryForXDayAhead(String categoryId, int numOfDays, int page);

     public List<CategoriesVO.Category> getCategories();

}
