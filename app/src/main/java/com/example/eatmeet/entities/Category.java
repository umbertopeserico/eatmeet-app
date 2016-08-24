package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * Created by sofia on 08/06/2016.
 */
public class Category extends AbstractEntity implements Serializable {
    // Instance variables
    private String name;
    private String image;
    private String imageTmpPath;
    private Integer events_count;

    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        this.getCs().firePropertyChange("name", oldValue, name);
    }

    public String getImage(){
        return this.image;
    }

    public void setImage(String image){
        String oldValue = this.image;
        this.image = image;
        this.getCs().firePropertyChange("image", oldValue, image);
    }

    public Integer getEventsCount() {
        return events_count;
    }

    public void setEventsCount(Integer eventsCount) {
        Integer oldValue = this.events_count;
        this.events_count = eventsCount;
        this.getCs().firePropertyChange("events_count", oldValue, eventsCount);
    }

    public String getImageTmpPath() {
        return imageTmpPath;
    }

    public void setImageTmpPath(String imageTmpPath) {
        String oldValue = this.imageTmpPath;
        this.imageTmpPath = imageTmpPath;
        this.getCs().firePropertyChange("imageTmpPath", oldValue, this.imageTmpPath);
    }

    @Override
    public String toString() {
        return getName();
    }
}
