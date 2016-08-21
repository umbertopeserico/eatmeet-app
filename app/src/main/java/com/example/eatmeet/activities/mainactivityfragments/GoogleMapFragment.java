package com.example.eatmeet.activities.mainactivityfragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activities.MainActivity;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.RestaurantDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.Restaurant;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;
import com.example.eatmeet.utils.Refreshable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleMapFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback,Refreshable {
    final int MY_PERMISSIONS_REQUEST_ACCESS_POSITION = 12;
    MapView mapView;
    HashMap<Marker,Restaurant> markers = new HashMap<>();
    GoogleMap map;
    View view;
    private List<Restaurant> restaurantList;
    LayoutInflater infoWindowLayoutInflater;
    ViewGroup infoWindowContainer;

    public GoogleMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        infoWindowContainer = container;
        infoWindowLayoutInflater = inflater;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) view.findViewById(R.id.googleMapView);
        mapView.onCreate(savedInstanceState);

        if (mapView != null)
            mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Context context = getContext();
        MainActivity mainActivity = (MainActivity) context;
        EatMeetApp.getFiltersManager().resetFilters();
        EatMeetApp.getFiltersManager().addRestaurant(markers.get(marker));
        mainActivity.setCurrentFragment(1);

        //String title = marker.getTitle();
        //Toast.makeText(this.getContext(), title + " clicked " + Integer.toString(restaurantId),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (map == null) {
            System.out.println("ERROR map == null");
            return;
        }
        /*
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_POSITION);
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_POSITION);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        */
        ifAllowedGetData();
    }

    private void ifAllowedGetData(){
        System.out.println("MAP READY TO LOAD DATA");
        RestaurantDAO restaurantDAO = EatMeetApp.getDaoFactory().getRestaurantDAO();
        final ObservableArrayList<Event> restaurantList = new ObservableArrayList<>();

        BackendStatusManager backendStatusManager = new BackendStatusManager();
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Logger.getLogger(GoogleMapFragment.this.getClass().getName()).log(Level.INFO, "Connection succeded");
            }

            @Override
            public void onFailure(Object response, Integer code) {
                Logger.getLogger(GoogleMapFragment.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
            }
        });

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_POSITION);
            return;
        }
        map.setMyLocationEnabled(true);
        // Setting a custom info window adapter for the google map
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }
            @Override
            public View getInfoContents(Marker arg0) {
                View infoWindowView = infoWindowLayoutInflater.inflate(R.layout.fragment_map_info_window, infoWindowContainer, false);
                /*
                LatLng latLng = arg0.getPosition();
                TextView tvLat = (TextView) infoWindowView.findViewById(R.id.tv_lat);
                tvLat.setText("Latitude:" + latLng.latitude);
                */
                TextView textViewTitle = (TextView) infoWindowView.findViewById(R.id.fragment_map_info_window_title);
                textViewTitle.setText(arg0.getTitle());
                TextView textViewDescription = (TextView) infoWindowView.findViewById(R.id.fragment_map_info_window_description);
                textViewDescription.setText(arg0.getSnippet());
                return infoWindowView;
            }
        });
        map.setOnInfoWindowClickListener(this);

        restaurantList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                Restaurant r = (Restaurant) item;
                Float markerColor = BitmapDescriptorFactory.HUE_RED;
                Float markerAlpha = 1.0f;
                if(r.getEvents().size()==0){
                    markerAlpha = 0.5f;
                }
                LatLng pos = new LatLng(r.getLat(), r.getLgt());
                MarkerOptions markerOptions = new MarkerOptions()
                        .title(r.getName())
                        .snippet("\n" + r.getEvents().size() + " eventi.\n\n" + r.getDescription())
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColor))
                        .alpha(markerAlpha)
                        .position(pos);
                Marker marker = map.addMarker(markerOptions);
                markers.put(marker, r);
                if(restaurantList.size()==1) {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(((Restaurant) restaurantList.get(0)).getLat(), ((Restaurant) restaurantList.get(0)).getLgt()), 12.0f));
                }
            }
        });

        restaurantDAO.getRestaurants(restaurantList, backendStatusManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null)
            mapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_POSITION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    System.out.println("permission was granted, yay! Do the");
                    ifAllowedGetData();
                    // contacts-related task you need to do.

                } else {

                    System.out.println("permission denied, boo! Disable the");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void refresh() {

    }
}
