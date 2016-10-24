package com.whatson.application;

import com.whatson.domain.CategoriesVO;
import com.whatson.domain.EventVO;

import java.util.List;

/**
 * Event application service interface
 */
public interface EventService {

    List<EventVO> getNext7DaysEvents(String categoryId, int page);

    List<CategoriesVO.Category> getCategories();
}
