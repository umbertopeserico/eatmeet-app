package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sofia on 08/06/2016.
 */
public class User extends AbstractEntity implements Serializable{
    private String name;
    private String surname;
    private String fullName;
    private String email;

    @Override
    protected void setPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    protected void unsetPropertyChangeListener(PropertyChangeListener listener) {

    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        return getEmail().equals(user.getEmail());

    }

    @Override
    public int hashCode() {
        int result = getEmail().hashCode();
        result = 31 * result + getId();
        return result;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
