package com.example.eatmeet.activities.myeventsactivityfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eatmeet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PastEventsFragment extends Fragment {


    public PastEventsFragment() {
        // Required empty public constructor
    }

    private void loadData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_events, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
