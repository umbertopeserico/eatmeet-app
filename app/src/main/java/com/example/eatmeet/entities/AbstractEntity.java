package com.example.eatmeet.entities;

import com.example.eatmeet.observablearraylist.ObservableArrayList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by umberto on 11/07/16.
 */
public abstract class AbstractEntity {

    private Integer id;
    private Date createdAt;
    private Date updatedAt;
    private PropertyChangeListener listener;

    private final PropertyChangeSupport idChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport createdAtChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport updatedAtChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport errorsChangeSupport = new PropertyChangeSupport(this);

    private HashMap<String, List<String>> errors = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldValue = this.id;
        this.id = id;
        this.idChangeSupport.firePropertyChange("id",oldValue, id);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        Date oldValue = this.createdAt;
        this.createdAt = createdAt;
        this.idChangeSupport.firePropertyChange("createdAt",oldValue, createdAt);
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        Date oldValue = this.updatedAt;
        this.updatedAt = updatedAt;
        this.idChangeSupport.firePropertyChange("updatedAt",oldValue, updatedAt);
    }

    public HashMap<String, List<String>> getErrors() {
        return errors;
    }

    public void addError(String field, String error) {
        List<String> errorsOfField = errors.get(field);
        if(errorsOfField == null) {
            errorsOfField = new ArrayList<>();
            errorsOfField.add(error);
            errors.put(field, errorsOfField);
        } else {
            errorsOfField.add(error);
        }
        this.errorsChangeSupport.firePropertyChange("addError", error+"Ol", error);
    }

    public void cleanErrors() {
        errors.clear();
    }

    public final void addPropertyChangeListener(PropertyChangeListener listener) {
        this.idChangeSupport.addPropertyChangeListener(listener);
        this.createdAtChangeSupport.addPropertyChangeListener(listener);
        this.updatedAtChangeSupport.addPropertyChangeListener(listener);
        this.listener = listener;
        this.errorsChangeSupport.addPropertyChangeListener(listener);
        this.setPropertyChangeListener(listener);
    }

    public final void removePropertyChangeListener(PropertyChangeListener listener) {
        this.idChangeSupport.removePropertyChangeListener(listener);
        this.createdAtChangeSupport.removePropertyChangeListener(listener);
        this.updatedAtChangeSupport.removePropertyChangeListener(listener);
        this.errorsChangeSupport.removePropertyChangeListener(listener);
        this.unsetPropertyChangeListener(listener);
    }

    public PropertyChangeListener getListener() {
        return listener;
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
