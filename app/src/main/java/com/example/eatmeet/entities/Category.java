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

    // PropertyChangeSupport for instance variables
    private final PropertyChangeSupport nameChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport imageChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport metaChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport eventsChangeSupport = new PropertyChangeSupport(this);

    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getImage(){
        return this.image;
    }

    public void setImage(String image){
        this.image = image;
    }

    @Override
    protected void setPropertyChangeListener(PropertyChangeListener listener) {
        this.nameChangeSupport.addPropertyChangeListener(listener);
        this.imageChangeSupport.addPropertyChangeListener(listener);
        this.metaChangeSupport.addPropertyChangeListener(listener);
        this.eventsChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    protected void unsetPropertyChangeListener(PropertyChangeListener listener) {
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
