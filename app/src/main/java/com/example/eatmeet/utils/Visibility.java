package com.example.eatmeet.utils;

import android.view.View;

/**
 * Created by sofia on 16/06/2016.
 */
public class Visibility {

    public Visibility(){}

    public boolean isVisible(View view){
        if(view.getVisibility()==View.VISIBLE){
            return true;
        } else {
            return false;
        }
    }

    public void makeInvisible(View view){
        view.setVisibility(View.GONE);
    }

    public void makeVisible(View view){
        view.setVisibility(View.VISIBLE);
    }

    public void toggleView(View view){
        if(view.getVisibility()==View.GONE)
            view.setVisibility(View.VISIBLE);
        else if(view.getVisibility()==View.VISIBLE)
            view.setVisibility(View.GONE);
    }
}
