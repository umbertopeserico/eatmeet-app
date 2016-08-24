package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public class User extends AbstractEntity implements Serializable{
    private String name;
    private String surname;
    private String fullName;
    private String email;
    private String password;
    private String passwordConfirmation;
    private String currentPassword;
    private String provider;
    private String uid;
    private List<EventParticipation> eventParticipations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        this.getCs().firePropertyChange("name", oldValue, this.name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        String oldValue = this.surname;
        this.surname = surname;
        this.getCs().firePropertyChange("surname", oldValue, this.surname);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String full_name) {
        String oldValue = this.fullName;
        this.fullName = full_name;
        this.getCs().firePropertyChange("fullName", oldValue, this.fullName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldValue = this.email;
        this.email = email;
        this.getCs().firePropertyChange("email", oldValue, this.email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String oldValue = this.password;
        this.password = password;
        this.getCs().firePropertyChange("password", oldValue, this.password);
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        String oldValue = this.passwordConfirmation;
        this.passwordConfirmation = passwordConfirmation;
        this.getCs().firePropertyChange("passwordConfirmation", oldValue, this.passwordConfirmation);
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        String oldValue = this.currentPassword;
        this.currentPassword = currentPassword;
        this.getCs().firePropertyChange("oldPassword", oldValue, this.currentPassword);
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        String oldValue = this.provider;
        this.provider = provider;
        this.getCs().firePropertyChange("provider", oldValue, this.provider);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        String oldValue = this.uid;
        this.uid = uid;
        this.getCs().firePropertyChange("provider", oldValue, this.uid);
    }

    public List<EventParticipation> getEventParticipations() {
        return eventParticipations;
    }

    public void setEventParticipations(List<EventParticipation> eventParticipations) {
        List<EventParticipation> oldValue = eventParticipations;
        this.eventParticipations = eventParticipations;
        this.getCs().firePropertyChange("eventParticipations", oldValue, this.eventParticipations);
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
