package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by umberto on 02/08/16.
 */
public class EventParticipation extends AbstractEntity {

    private Integer userId;
    private Integer eventId;
    private User user;
    private Event event;
    private Integer seats;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        Integer oldValue = this.userId;
        this.userId = userId;
        this.getCs().firePropertyChange("userId",oldValue, this.userId);
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        Integer oldValue = this.eventId;
        this.eventId = eventId;
        this.getCs().firePropertyChange("eventId",oldValue, this.eventId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        User oldValue = this.user;
        this.user = user;
        this.getCs().firePropertyChange("user",oldValue, this.user);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        Event oldValue = this.event;
        this.event = event;
        this.getCs().firePropertyChange("event",oldValue, this.event);
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        Integer oldValue = this.seats;
        this.seats = seats;
        this.getCs().firePropertyChange("seats", oldValue, this.seats);
    }

}
