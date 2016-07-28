package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.entities.RestaurantOwner;

import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface RestaurantOwnerDAO {

    void getRestaurantOwners(List<RestaurantOwner> restaurantOwners, BackendStatusManager backendStatusManager);

    @Deprecated
    List<RestaurantOwner> getRestaurantOwners();
}
