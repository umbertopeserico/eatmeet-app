package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by umberto on 08/06/16.
 */
public class Menu extends AbstractEntity implements Serializable {

    private String title;
    private double maxPrice;
    private double minPrice;
    private String textMenu;
    private String htmlMenu;
    private int restaurantId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double max_price) {
        this.maxPrice = max_price;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double min_price) {
        this.minPrice = min_price;
    }

    public String getTextMenu() {
        return textMenu;
    }

    public void setTextMenu(String text_menu) {
        this.textMenu = text_menu;
    }

    public String getHtmlMenu() {
        return htmlMenu;
    }

    public void setHtmlMenu(String html_menu) {
        this.htmlMenu = html_menu;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurant_id) {
        this.restaurantId = restaurant_id;
    }

    @Override
    protected void setPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    protected void unsetPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (getId() != menu.getId()) return false;
        return getTitle().equals(menu.getTitle());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getTitle().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
