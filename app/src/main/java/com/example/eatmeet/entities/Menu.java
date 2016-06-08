package com.example.eatmeet.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by umberto on 08/06/16.
 */
public class Menu implements Serializable {

    private int id;
    private String title;
    private double max_price;
    private double min_price;
    private String text_menu;
    private String html_menu;
    private int restaurant_id;
    private Date created_at;
    private Date updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMaxPrice() {
        return max_price;
    }

    public void setMaxPrice(double max_price) {
        this.max_price = max_price;
    }

    public double getMinPrice() {
        return min_price;
    }

    public void setMinPrice(double min_price) {
        this.min_price = min_price;
    }

    public String getTextMenu() {
        return text_menu;
    }

    public void setTextMenu(String text_menu) {
        this.text_menu = text_menu;
    }

    public String getHtmlMenu() {
        return html_menu;
    }

    public void setHtmlMenu(String html_menu) {
        this.html_menu = html_menu;
    }

    public int getRestaurantId() {
        return restaurant_id;
    }

    public void setRestaurantId(int restaurant_id) {
        this.restaurant_id = restaurant_id;
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
