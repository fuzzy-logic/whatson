package com.whatson.application;

import com.whatson.infrastructure.CategoriesVO;
import com.whatson.infrastructure.EventVO;

import java.util.List;

/**
 * Event application service interface
 */
public interface EventService {

    List<EventVO> getNext7DaysEvents(String categoryId, int page);

    List<CategoriesVO.Category> getCategories();
}
