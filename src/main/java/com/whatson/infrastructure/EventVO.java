package com.whatson.infrastructure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by gawain on 22/10/2016.
 */
//@XmlRootElement(name = "event")
//@XmlType(propOrder = { "id", "title", "url" })
public class EventVO implements Serializable {


    private String id;
    private String title;
    private String url;


    //@XmlElement(name = "id")
    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String toString() {
        return "[EventVO] id=" + getId() +  ", title=" + getTitle();
    }
}
