package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by sofia on 08/06/2016.
 */
public class Category extends AbstractEntity implements Serializable {
    // Instance variables
    private String name;
    private String image;
    private String imageTmpPath;
    private Integer eventsCount;

    // PropertyChangeSupport for instance variables
    private final PropertyChangeSupport cs = new PropertyChangeSupport(this);
    private final PropertyChangeSupport nameChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport imageChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport metaChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport eventsCountChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport eventsChangeSupport = new PropertyChangeSupport(this);

    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        this.nameChangeSupport.firePropertyChange("name", oldValue, name);
    }

    public String getImage(){
        return this.image;
    }

    public void setImage(String image){
        String oldValue = this.image;
        this.image = image;
        this.imageChangeSupport.firePropertyChange("image", oldValue, image);
    }

    public Integer getEventsCount() {
        return eventsCount;
    }

    public void setEventsCount(Integer eventsCount) {
        Integer oldValue = this.eventsCount;
        this.eventsCount = eventsCount;
        this.eventsCountChangeSupport.firePropertyChange("eventsCount", oldValue, eventsCount);
    }

    public String getImageTmpPath() {
        return imageTmpPath;
    }

    public void setImageTmpPath(String imageTmpPath) {
        String oldValue = this.imageTmpPath;
        this.imageTmpPath = imageTmpPath;
        this.cs.firePropertyChange("imageTmpPath", oldValue, this.imageTmpPath);
    }

    @Override
    protected void setPropertyChangeListener(PropertyChangeListener listener) {
        this.cs.addPropertyChangeListener(listener);
        this.nameChangeSupport.addPropertyChangeListener(listener);
        this.imageChangeSupport.addPropertyChangeListener(listener);
        this.metaChangeSupport.addPropertyChangeListener(listener);
        this.eventsChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    protected void unsetPropertyChangeListener(PropertyChangeListener listener) {
        this.cs.removePropertyChangeListener(listener);
        this.nameChangeSupport.removePropertyChangeListener(listener);
        this.imageChangeSupport.removePropertyChangeListener(listener);
        this.metaChangeSupport.removePropertyChangeListener(listener);
        this.eventsChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return getName();
    }
}
