package com.example.eatmeet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by umberto on 08/06/16.
 */
public class Restaurant implements Serializable {

    private int id;
    private String name;
    private String description;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String zip_code;
    private String province;
    private String full_address;
    private int restaurant_owner_id;
    private Date created_at;
    private Date updated_at;
    private double lat;
    private double lgt;
    private List<Event> events = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zip_code;
    }

    public void setZipCode(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getFullAddress() {
        return full_address;
    }

    public void setFullAddress(String full_address) {
        this.full_address = full_address;
    }

    public int getRestaurantOwnerId() {
        return restaurant_owner_id;
    }

    public void setRestaurantOwnerId(int restaurant_owner_id) {
        this.restaurant_owner_id = restaurant_owner_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLgt() {
        return lgt;
    }

    public void setLgt(double lgt) {
        this.lgt = lgt;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        if (getId() != that.getId()) return false;
        return getEmail().equals(that.getEmail());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getEmail().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }
}
