package com.example.eatmeet.utils;

/**
 * Created by umberto on 08/06/16.
 */
public class Configs {

    private static final boolean development = false;

    public static String getBackendUrl() {
        if(development)
            return "http://umberto-desktop:3000/";
        else
            return "http://eatmeet.peserico.me/";
    }

}
