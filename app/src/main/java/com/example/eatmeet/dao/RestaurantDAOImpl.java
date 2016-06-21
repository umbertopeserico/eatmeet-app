package com.example.eatmeet.dao;

import android.os.SystemClock;
import android.provider.Settings;

import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.Restaurant;
import com.example.eatmeet.utils.Configs;
import com.example.eatmeet.utils.Connection;
import com.example.eatmeet.utils.Notificable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public class RestaurantDAOImpl implements RestaurantDAO {

    private Notificable mNotificable;

    public RestaurantDAOImpl(Notificable notificable) {
        this.mNotificable = notificable;
    }

    @Override
    public List<Restaurant> getRestaurants() {
        final ArrayList<Restaurant> restaurants = new ArrayList<>();

        Restaurant r1 = new Restaurant();
        r1.setId(1);
        r1.setName("Ristorante 1");
        r1.setLat(46.0646806);
        r1.setLgt(11.1212576);

        Restaurant r2 = new Restaurant();
        r2.setId(2);
        r2.setName("Ristorante 2");
        r2.setLat(46.0648169);
        r2.setLgt(11.1482693);

        final Restaurant r3 = new Restaurant();
        r3.setId(3);
        r3.setName("Ristorante 3");
        r3.setLat(46.0684497);
        r3.setLgt(11.1176831);

        Event e1= new Event();
        e1.setTitle("Evento Prova");


        r1.getEvents().add(e1);

        restaurants.add(r1);
        restaurants.add(r2);

        new Connection() {
            @Override
            public void onPostExecute(String result) {
                    SystemClock.sleep(10000);
                    restaurants.add(r3);
                mNotificable.sendNotify();
                System.out.println("END OF TASK");
            }
        }.execute(Configs.getBackendUrl()+"/api/restaurants");


        return restaurants;
    }

    @Override
    public Event getClosestEvent(Restaurant restaurant) {
        Event e1= new Event();
        e1.setTitle("Evento Prova più vicino");
        
        return e1;
    }
}
