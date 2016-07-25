package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by umberto on 08/06/16.
 */
public class RestaurantOwner extends AbstractEntity implements Serializable {

    private String name;
    private String surname;
    private String fullName;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String full_name) {
        this.fullName = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    protected void setPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    protected void unsetPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestaurantOwner that = (RestaurantOwner) o;

        if (getId() != that.getId()) return false;
        return getEmail().equals(that.getEmail());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getEmail().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.getFullName();
    }
}
