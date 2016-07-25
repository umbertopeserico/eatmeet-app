package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

/**
 * Created by umberto on 11/07/16.
 */
public abstract class AbstractEntity {

    private int id;
    private Date createdAt;
    private Date updatedAt;

    private final PropertyChangeSupport idChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport createdAtChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport updatedAtChangeSupport = new PropertyChangeSupport(this);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public final void addPropertyChangeListener(PropertyChangeListener listener) {
        this.idChangeSupport.addPropertyChangeListener(listener);
        this.createdAtChangeSupport.addPropertyChangeListener(listener);
        this.updatedAtChangeSupport.addPropertyChangeListener(listener);
        this.setPropertyChangeListener(listener);
    }

    public final void removePropertyChangeListener(PropertyChangeListener listener) {
        this.idChangeSupport.removePropertyChangeListener(listener);
        this.createdAtChangeSupport.removePropertyChangeListener(listener);
        this.updatedAtChangeSupport.removePropertyChangeListener(listener);
        this.unsetPropertyChangeListener(listener);
    }

    /**
     * Method to add a listener to other properties. This method is called by addPropertyChangeListener(PropertyChangeListener listener)
     */
    protected abstract void setPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Method to remove a listener to other properties. This method is called by removePropertyChangeListener(PropertyChangeListener listener)
     */
    protected abstract void unsetPropertyChangeListener(PropertyChangeListener listener);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEntity that = (AbstractEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
