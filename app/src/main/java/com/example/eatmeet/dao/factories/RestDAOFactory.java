package com.example.eatmeet.dao.factories;

import com.example.eatmeet.dao.implementations.rest.CategoryDAORest;
import com.example.eatmeet.dao.implementations.rest.EventDAORest;
import com.example.eatmeet.dao.implementations.rest.RestaurantDAORest;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.dao.interfaces.MenuDAO;
import com.example.eatmeet.dao.interfaces.RestaurantDAO;
import com.example.eatmeet.dao.interfaces.RestaurantOwnerDAO;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.dao.interfaces.ZoneDAO;

/**
 * Created by umberto on 02/07/16.
 */
public class RestDAOFactory implements DAOFactory {

    @Override
    public CategoryDAO getCategoryDAO() {
        return new CategoryDAORest();
    }

    @Override
    public EventDAO getEventDAO() {
        return new EventDAORest();
    }

    @Override
    public MenuDAO getMenuDAO() {
        return null;
    }

    @Override
    public RestaurantDAO getRestaurantDAO() {
        return new RestaurantDAORest();
    }

    @Override
    public RestaurantOwnerDAO getRestaurantOwnerDAO() {
        return null;
    }

    @Override
    public UserDAO getUserDAO() {
        return null;
    }

    @Override
    public ZoneDAO getZoneDAO() {
        return null;
    }

}
