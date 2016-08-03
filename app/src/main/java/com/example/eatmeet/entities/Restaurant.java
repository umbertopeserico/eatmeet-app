package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by umberto on 08/06/16.
 */
public class Restaurant extends AbstractEntity implements Serializable {

    private String name;
    private String description;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String zipCode;
    private String province;
    private String fullAddress;
    private Integer restaurantOwnerId;
    private Double lat;
    private Double lgt;

    private List<Event> events;
    private Integer pastEventsCount;
    private Integer futureEventsCount;

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
        return zipCode;
    }

    public void setZipCode(String zip_code) {
        this.zipCode = zip_code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String full_address) {
        this.fullAddress = full_address;
    }

    public Integer getRestaurantOwnerId() {
        return restaurantOwnerId;
    }

    public void setRestaurantOwnerId(Integer restaurant_owner_id) {
        this.restaurantOwnerId = restaurant_owner_id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLgt() {
        return lgt;
    }

    public void setLgt(Double lgt) {
        this.lgt = lgt;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Integer getPastEventsCount() {
        return pastEventsCount;
    }

    public void setPastEventsCount(Integer pastEventsCount) {
        this.pastEventsCount = pastEventsCount;
    }

    public Integer getFutureEventsCount() {
        return futureEventsCount;
    }

    public void setFutureEventsCount(Integer futureEventsCount) {
        this.futureEventsCount = futureEventsCount;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    protected void setPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    protected void unsetPropertyChangeListener(PropertyChangeListener listener) {

    }
}
