package com.example.eatmeet.activities.myeventsactivityfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eatmeet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FutureEventsFragment extends Fragment {


    public FutureEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_future_events, container, false);
    }

}
