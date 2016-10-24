package com.whatson.infrastructure;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Represents search results wrapper object returned by eventful api
 */

@XmlRootElement(name = "search")
@XmlSeeAlso({EventVO.class})
public class EventSearchResult {

    int totalItems;
    int searchTime;
    int pageSize;
    int pageCount;
    int pageNumber;
    List<EventVO> events;

    @XmlElement(name = "total_items")
    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }


    @XmlElement(name = "search_time")
    public int getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(int searchTime) {
        this.searchTime = searchTime;
    }

    @XmlElement(name = "page_size")
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @XmlElement(name = "page_count")
    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @XmlElement(name = "page_number")
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    public List<EventVO> getEvents() {
        return events;
    }

    public void setEvents(List<EventVO> events) {
        this.events = events;
    }

    public String toString() {
        return "[EventSearchResult] totalItems=" + getTotalItems() +  ", pageSize=" + getPageSize() + ", searchTime=" + getSearchTime();
    }
}
