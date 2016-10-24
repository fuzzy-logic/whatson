package com.whatson.infrastructure;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Jaxb Value object for marhsalling to eventful category format
 */
@XmlRootElement(name = "categories")
public class CategoriesVO {


    private List<Category> categories;

    //@XmlElementWrapper(name  = "categories")
    @XmlElement(name = "category")
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public static class Category {
        private String id;
        private String name;

        @XmlElement(name = "id")
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @XmlElement(name = "name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            return id + ": " + name;
        }
    }
}
