package com.example.eatmeet.entities;

import java.io.Serializable;

/**
 * Created by umberto on 26/05/16.
 */
public class Zone implements Serializable {

    private String name;

    public Zone(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zone zone = (Zone) o;

        return name.equals(zone.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
