package com.example.eatmeet.mainactivityfragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eatmeet.R;
import com.example.eatmeet.activities.MainActivity;
import com.example.eatmeet.dao.interfaces.RestaurantDAO;
import com.example.eatmeet.dao.implementations.oldrest.RestaurantDAOImpl;
import com.example.eatmeet.entities.Restaurant;
import com.example.eatmeet.utils.Notificable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleMapFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback, Notificable {
    final int MY_PERMISSIONS_REQUEST_ACCESS_POSITION = 12;
    MapView mapView;
    HashMap<Marker,Integer> markers = new HashMap<>();
    GoogleMap map;
    private List<Restaurant> restaurantList;

    public GoogleMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

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
        ArrayList<Integer> f_restaurants = new ArrayList<>();
        f_restaurants.add(markers.get(marker));
        mainActivity.removeAllFilters();
        mainActivity.setF_restaurants(f_restaurants);
        mainActivity.setCurrentFragment(1);

        //String title = marker.getTitle();
        //Toast.makeText(this.getContext(), title + " clicked " + Integer.toString(restaurantId),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (map == null) {
            System.out.println("ERROR 1");
            return;
        }

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
        ifAllowedGetData();
    }

    private void ifAllowedGetData(){
        System.out.println("MAP READY TO LOAD DATA");
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl(this);
        restaurantList = restaurantDAO.getRestaurants();
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
    public void sendNotify() {
        System.out.println("ENDLOAD DATA. STARTING CALLBACK");
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                //if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                //} else {
                    // No explanation needed, we can request the permission.
                    //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_POSITION);
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_POSITION);
                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                //}
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        System.out.println("ENDLOAD DATA. STARTING CALLBACK");
        map.setMyLocationEnabled(true);
        map.setOnInfoWindowClickListener(this);

        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        for(Restaurant r : this.restaurantList) {
            LatLng pos = new LatLng(r.getLat(), r.getLgt());
            System.err.println("FROM DAO REST"+r.getName());
            MarkerOptions markerOptions = new MarkerOptions()
                    .title(r.getName())
                    .snippet(r.getDescription())
                    .position(pos);
            Marker marker = map.addMarker(markerOptions);
            markers.put(marker,r.getId());
        }

        if(restaurantList.size()>0) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(restaurantList.get(0).getLat(), restaurantList.get(0).getLgt()), 12.0f));
        }

    }
}
