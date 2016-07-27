package com.example.eatmeet.backendstatuses;

/**
 * Created by umberto on 25/07/16.
 */
public interface BackendStatusListener {

    void onSuccess(Object response, Integer code);
    void onFailure(Object response, Integer code);

}
