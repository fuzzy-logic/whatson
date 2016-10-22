package com.whatson.application;

import com.whatson.infrastructure.EventVO;

import java.util.List;

/**
 * Created by gawain on 22/10/2016.
 */
public interface EventService {

    List<EventVO> getNext7DaysEvents();
}
