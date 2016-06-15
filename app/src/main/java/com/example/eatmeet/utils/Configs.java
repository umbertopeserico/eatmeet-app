package com.example.eatmeet.utils;

/**
 * Created by umberto on 08/06/16.
 */
public class Configs {

    private static final boolean development = true;

    public static String getBackendUrl() {
        if(development)
            return "http://eatmeet.herokuapp.com/";
        else
            return "http://eatmeet.herokuapp.com/";
    }

}
