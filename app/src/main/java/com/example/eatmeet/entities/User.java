package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    private String password;

    private PropertyChangeSupport cs = new PropertyChangeSupport(this);

    @Override
    protected void setPropertyChangeListener(PropertyChangeListener listener) {
        this.cs.addPropertyChangeListener(listener);
    }

    @Override
    protected void unsetPropertyChangeListener(PropertyChangeListener listener) {
        this.cs.addPropertyChangeListener(listener);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        this.cs.firePropertyChange("name", oldValue, this.name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        String oldValue = this.surname;
        this.surname = surname;
        this.cs.firePropertyChange("surname", oldValue, this.surname);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String full_name) {
        String oldValue = this.fullName;
        this.fullName = full_name;
        this.cs.firePropertyChange("fullName", oldValue, this.fullName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldValue = this.email;
        this.email = email;
        this.cs.firePropertyChange("email", oldValue, this.email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String oldValue = this.password;
        this.password = password;
        this.cs.firePropertyChange("password", oldValue, this.password);
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
