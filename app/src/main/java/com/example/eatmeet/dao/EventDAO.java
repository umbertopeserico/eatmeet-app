package com.example.eatmeet.dao;

import com.example.eatmeet.entities.Event;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface EventDAO {
    public List<Event> getEvents(JSONObject parameters);
    public Event getEventById(int id);
}
