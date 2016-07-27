package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.Restaurant;

import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface RestaurantDAO {

    void getRestaurants(final List<Restaurant> restaurants, final BackendStatusManager backendStatusManager);

    @Deprecated
    List<Restaurant> getRestaurants();
    @Deprecated
    Event getClosestEvent(Restaurant restaurant);
}
