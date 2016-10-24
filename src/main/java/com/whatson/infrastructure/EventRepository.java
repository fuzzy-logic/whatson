package com.whatson.infrastructure;

import java.util.List;

/**
 * Retrieve events from http://api.eventful.com
 */
public interface EventRepository {

     public List<EventVO> getEventsXDaysAhead(String categoryId, int numOfDays, int page);

     public List<CategoriesVO.Category> getCategories();

}
