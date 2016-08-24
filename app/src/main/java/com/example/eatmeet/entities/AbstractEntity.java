package com.example.eatmeet.entities;

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
    private final PropertyChangeSupport cs = new PropertyChangeSupport(this);
    private HashMap<String, List<String>> errors = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldValue = this.id;
        this.id = id;
        this.cs.firePropertyChange("id",oldValue, id);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        Date oldValue = this.createdAt;
        this.createdAt = createdAt;
        this.cs.firePropertyChange("createdAt",oldValue, createdAt);
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        Date oldValue = this.updatedAt;
        this.updatedAt = updatedAt;
        this.cs.firePropertyChange("updatedAt",oldValue, updatedAt);
    }

    public PropertyChangeSupport getCs() {
        return cs;
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
        this.cs.firePropertyChange("addError", error+"Ol", error);
    }

    public void cleanErrors() {
        errors.clear();
    }

    public final void addPropertyChangeListener(PropertyChangeListener listener) {
        this.cs.addPropertyChangeListener(listener);
    }

    public final void removePropertyChangeListener(PropertyChangeListener listener) {
        this.cs.removePropertyChangeListener(listener);
    }

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
