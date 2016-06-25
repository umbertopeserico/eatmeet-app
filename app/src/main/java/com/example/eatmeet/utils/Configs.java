package com.example.eatmeet.utils;

/**
 * Created by umberto on 08/06/16.
 */
public class Configs {

    private static final boolean development = false;

    public static String getBackendUrl() {
        if(development)
            return "http://eatmeet.herokuapp.com/";
        else
            return "http://192.168.1.70:3000/";
    }

}
