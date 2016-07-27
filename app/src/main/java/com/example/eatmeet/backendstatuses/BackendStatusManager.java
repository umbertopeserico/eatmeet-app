package com.example.eatmeet.backendstatuses;

import com.example.eatmeet.observablearraylist.OnAddListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umberto on 25/07/16.
 */
public class BackendStatusManager {

    private ArrayList<BackendStatusListener> listeners;

    public BackendStatusManager() {
        listeners = new ArrayList<>();
    }

    public void addError(Object error, Integer code) {
        notifyErrorsListeners(error, code);
    }

    public void addSuccess(Object error, Integer code) {
        notifySuccessListeners(error, code);
    }

    private void notifyErrorsListeners(Object response, Integer code) {
        for(BackendStatusListener bel : listeners) {
            bel.onFailure(response, code);
        }
    }

    private void notifySuccessListeners(Object response, Integer code) {
        for(BackendStatusListener bel : listeners) {
            bel.onSuccess(response, code);
        }
    }

    public void setBackendStatusListener(BackendStatusListener listener) {
        this.listeners.add(listener);
    }
}
