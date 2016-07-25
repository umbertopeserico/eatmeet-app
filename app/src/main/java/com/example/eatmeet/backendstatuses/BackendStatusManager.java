package com.example.eatmeet.backendstatuses;

import com.example.eatmeet.observablearraylist.OnAddListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umberto on 25/07/16.
 */
public class BackendStatusManager {

    private ArrayList<BackendStatusListener> listeners;
    private List<String> errors;
    private List<String> successes;

    public BackendStatusManager() {
        errors = new ArrayList<>();
        successes = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public void addError(String error) {
        if(errors.add(error)) {
            notifyErrorsListeners();
        }
    }

    public void addSuccess(String error) {
        if(successes.add(error)) {
            notifySuccessListeners();
        }
    }

    private void notifyErrorsListeners() {
        for(BackendStatusListener bel : listeners) {
            bel.onFailure();
        }
    }

    private void notifySuccessListeners() {
        for(BackendStatusListener bel : listeners) {
            bel.onSuccess();
        }
    }

    public void setBackendStatusListener(BackendStatusListener listener) {
        this.listeners.add(listener);
    }
}
