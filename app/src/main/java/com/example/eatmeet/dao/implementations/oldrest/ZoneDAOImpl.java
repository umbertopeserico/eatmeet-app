package com.example.eatmeet.dao.implementations.oldrest;

import com.example.eatmeet.dao.interfaces.ZoneDAO;
import com.example.eatmeet.entities.Zone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umberto on 26/05/16.
 */
public class ZoneDAOImpl implements ZoneDAO {
    @Override
    public void getZones(List<Zone> zones) {

    }

    @Override
    public List<Zone> getZones() {
        ArrayList<Zone> zones = new ArrayList<>();

        return zones;
    }
}
