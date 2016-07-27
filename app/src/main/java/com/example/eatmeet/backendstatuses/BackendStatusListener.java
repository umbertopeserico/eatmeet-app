package com.example.eatmeet.backendstatuses;

/**
 * Created by umberto on 25/07/16.
 */
public interface BackendStatusListener {

    void onSuccess(String response, Integer code);
    void onFailure(String response, Integer code);

}
