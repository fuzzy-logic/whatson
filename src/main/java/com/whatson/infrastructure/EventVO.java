package com.whatson.infrastructure;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Jaxb value object to marshall/unmarshall eventful api xml
 */
public class EventVO implements Serializable {


    private String id;
    private String title;
    private String description;
    private String url;
    private String venueName;
    private String venueAddress;
    private Date startDate;
    private Date endDate;
    private Date created;
    private Date modified;
    private Image image;
    private List<CategoriesVO.Category> categories;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getStartDate() {
        return startDate;
    }

    public String getStartDateString() {
        if (startDate == null) return "";
        return dateFormat.format(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(name = "stop_time")
    public Date getEndDate() {
        return endDate;
    }

    public String getEndDateString() {
        if (endDate == null) return "";
        return dateFormat.format(endDate);
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

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "venue_name")
    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @XmlElement(name = "venue_address")
    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    @XmlElementWrapper(name  = "categories")
    @XmlElement(name = "category")
    public List<CategoriesVO.Category> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesVO.Category> categories) {
        this.categories = categories;
    }

    public String[] getCategoryIds() {
        String[] categories = new String[getCategories().size()];
        for (int i = 0 ; i < getCategories().size() ; i++) {
            categories[i] = getCategories().get(i).getId();
        }
        return categories;
    }

    public String toString() {
        return "[EventVO] id=" + getId() +  ", title=" + getTitle() +
                ", startDate=" + getStartDateString() +
                ", endDate=" + getEndDateString();
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
