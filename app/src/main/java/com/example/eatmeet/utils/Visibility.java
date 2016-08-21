package com.example.eatmeet.utils;

import android.view.View;

/**
 * Created by sofia on 16/06/2016.
 */
public class Visibility {

    private Visibility(){}

    public static boolean isVisible(View view){
        if(view.getVisibility()==View.VISIBLE){
            return true;
        } else {
            return false;
        }
    }

    public static void makeInvisible(View view){
        view.setVisibility(View.GONE);
    }

    public static void makeVisible(View view){
        view.setVisibility(View.VISIBLE);
    }

    public static void toggleView(View view){
        if(view.getVisibility()==View.GONE)
            view.setVisibility(View.VISIBLE);
        else if(view.getVisibility()==View.VISIBLE)
            view.setVisibility(View.GONE);
    }
}
