package com.example.eatmeet.dao.factories;

import com.example.eatmeet.dao.interfaces.*;

/**
 * Created by umberto on 02/07/16.
 */
public interface DAOFactory {

    CategoryDAO getCategoryDAO();
    EventDAO getEventDAO();
    MenuDAO getMenuDAO();
    RestaurantDAO getRestaurantDAO();
    RestaurantOwnerDAO getRestaurantOwnerDAO();
    UserDAO getUserDAO();
    ZoneDAO getZoneDAO();

}
