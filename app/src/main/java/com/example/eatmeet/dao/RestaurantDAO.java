package com.example.eatmeet.dao;

import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.Restaurant;

import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface RestaurantDAO {
    public List<Restaurant> getRestaurants();
    public Event getClosestEvent(Restaurant restaurant);
}
