package com.example.eatmeet.dao;

import com.example.eatmeet.entities.Zone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umberto on 26/05/16.
 */
public class ZoneDAOImpl implements ZoneDAO {
    @Override
    public List<Zone> getZones() {
        ArrayList<Zone> zones = new ArrayList<>();

        zones.add(new Zone("Test Zone"));

        return zones;
    }
}