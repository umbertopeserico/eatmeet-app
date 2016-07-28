package com.example.eatmeet.dao.implementations.oldrest;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.RestaurantOwnerDAO;
import com.example.eatmeet.entities.RestaurantOwner;

import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public class RestaurantOwnerDAOImpl implements RestaurantOwnerDAO {
    @Override
    public void getRestaurantOwners(List<RestaurantOwner> restaurantOwners, BackendStatusManager backendStatusManager) {

    }

    @Override
    public List<RestaurantOwner> getRestaurantOwners() {
        return null;
    }
}
