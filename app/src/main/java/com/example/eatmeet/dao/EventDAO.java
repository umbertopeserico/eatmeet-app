package com.example.eatmeet.dao;

import com.example.eatmeet.entities.Event;

import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface EventDAO {
    public List<Event> getEvents();
}