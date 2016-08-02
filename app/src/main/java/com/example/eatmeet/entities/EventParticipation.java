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

    private PropertyChangeSupport cs = new PropertyChangeSupport(this);

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        Integer oldValue = this.userId;
        this.userId = userId;
        this.cs.firePropertyChange("userId",oldValue, this.userId);
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        Integer oldValue = this.eventId;
        this.eventId = eventId;
        this.cs.firePropertyChange("eventId",oldValue, this.eventId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        User oldValue = this.user;
        this.user = user;
        this.cs.firePropertyChange("user",oldValue, this.user);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        Event oldValue = this.event;
        this.event = event;
        this.cs.firePropertyChange("event",oldValue, this.event);
    }

    @Override
    protected void setPropertyChangeListener(PropertyChangeListener listener) {
        cs.addPropertyChangeListener(listener);
    }

    @Override
    protected void unsetPropertyChangeListener(PropertyChangeListener listener) {
        cs.removePropertyChangeListener(listener);
    }
}
