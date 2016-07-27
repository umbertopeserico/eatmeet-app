package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.entities.Event;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface EventDAO {

    void getEvents(final List<Event> events, final BackendStatusManager backendStatusManager, JSONObject parameters);

    @Deprecated
    List<Event> getEvents(JSONObject parameters);
    @Deprecated
    Event getEventById(int id);
}
