package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.entities.Zone;

import java.util.List;

/**
 * Created by umberto on 26/05/16.
 */
public interface ZoneDAO {

    void getZones(final List<Zone> zones);

    @Deprecated
    public List<Zone> getZones();

}