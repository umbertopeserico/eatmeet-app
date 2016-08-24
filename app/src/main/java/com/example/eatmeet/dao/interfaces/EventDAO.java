package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.entities.Event;

import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface EventDAO {

    /**
     * This function gets the image for the event
     * @param url remote url for image
     * @param filePath file path on the client filesystem
     * @param backendStatusManager the backend status manager
     * @param cacheDir the dir where to save he file
     */
    void getImage(String url, String filePath, final BackendStatusManager backendStatusManager, File cacheDir);

    /**
     * This method is used to get the events from the implemented backend.
     * It takes an output parameter as first which is the list that will be filled by the implementation
     * @param events the list to fill
     * @param backendStatusManager the backend status manager
     * @param parameters the JSONObject parameter for filtering
     */
    void getEvents(final List<Event> events, final BackendStatusManager backendStatusManager, JSONObject parameters);

    /**
     * This method is used to get an event from the backend.
     * It takes an output parameter to fill with data retrieved
     * @param backendStatusManager the backend status manager
     */
    void getEvent(final Integer id, final BackendStatusManager backendStatusManager);

    void addParticipation(Event event, Integer participants, BackendStatusManager backendStatusManager);

    void removeParticipation(Event event, BackendStatusManager backendStatusManager);

}
