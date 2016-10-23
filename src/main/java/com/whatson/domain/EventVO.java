package com.whatson.domain;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Jaxb value object to marshall/unmarshall eventful api xml
 */
public class EventVO implements Serializable {


    private String id;
    private String title;
    private String url;
    private Date startDate;
    private Date endDate;
    private Date created;
    private Date modified;
    private Image image;


    public String getImageUrl() {
        return this.image.getUrl();
    }

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

    @XmlElement(name = "start_time")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlElement(name = "stop_time")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @XmlElement(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @XmlElement(name = "modified")
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @XmlElement(name = "image")
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String toString() {
        return "[EventVO] id=" + getId() +  ", title=" + getTitle();
    }


    public static class Image {
        private String url;
        private int width;
        private int height;
        private Image thumb;
        private Image small;
        private Image medium;

        @XmlElement(name = "url")
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @XmlElement(name = "width")
        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        @XmlElement(name = "height")
        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @XmlElement(name = "thumb")
        public Image getThumb() {
            return thumb;
        }

        public void setThumb(Image thumb) {
            this.thumb = thumb;
        }

        @XmlElement(name = "small")
        public Image getSmall() {
            return small;
        }

        public void setSmall(Image small) {
            this.small = small;
        }

        @XmlElement(name = "medium")
        public Image getMedium() {
            return medium;
        }

        public void setMedium(Image medium) {
            this.medium = medium;
        }
    }
}
