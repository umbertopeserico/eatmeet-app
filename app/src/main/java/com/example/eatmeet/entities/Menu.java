package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by umberto on 08/06/16.
 */
public class Menu extends AbstractEntity implements Serializable {

    private String title;
    private Double maxPrice;
    private Double minPrice;
    private String textMenu;
    private String htmlMenu;
    private Integer restaurantId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String oldValue = this.title;
        this.title = title;
        this.getCs().firePropertyChange("title", oldValue, this.title);
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double max_price) {
        Double oldValue = this.maxPrice;
        this.maxPrice = max_price;
        this.getCs().firePropertyChange("maxPrice", oldValue, this.maxPrice);
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        Double oldValue = this.minPrice;
        this.minPrice = minPrice;
        this.getCs().firePropertyChange("minPrice", oldValue, this.minPrice);
    }

    public String getTextMenu() {
        return textMenu;
    }

    public void setTextMenu(String textMenu) {
        String oldValue = this.textMenu;
        this.textMenu = textMenu;
        this.getCs().firePropertyChange("textMenu", oldValue, this.textMenu);
    }

    public String getHtmlMenu() {
        return htmlMenu;
    }

    public void setHtmlMenu(String htmlMenu) {
        String oldValue = this.htmlMenu;
        this.htmlMenu = htmlMenu;
        this.getCs().firePropertyChange("htmlMenu", oldValue, this.htmlMenu);
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        Integer oldValue = this.restaurantId;
        this.restaurantId = restaurantId;
        this.getCs().firePropertyChange("restaurantId", oldValue, this.restaurantId);
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
