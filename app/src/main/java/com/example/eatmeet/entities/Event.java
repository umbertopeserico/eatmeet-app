package com.example.eatmeet.entities;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by sofia on 08/06/2016.
 */
public class Event implements Serializable{
    private int id=-1;
    private String title;
    private Date schedule;
    private int max_people=-1;
    private int min_people=-1;
    private int min_price_people=-1;
    private double max_price=-1;
    private double actual_price=-1;
    private double min_price=-1;
    private ArrayList<Double> prices_array;
    private int menu_id=-1;
    private Date created_at;
    private Date updated_at;
    private int participants=-1;
    private String urlImage;
    private String urlImageThumb;
    private String urlImageMedium;

    private Menu menu;
    private Restaurant restaurant;
    private ArrayList<Category> categories;

    private final PropertyChangeSupport idChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport titleChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport scheduleChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport max_peopleChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport min_peopleChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport min_price_peopleChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport max_priceChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport actual_priceChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport min_priceChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport prices_arrayChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport menu_idChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport participantsChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport menuChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport restaurantChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport categoriesChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport urlImageChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport urlImageThumbChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport urlImageMediumChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.idChangeSupport.addPropertyChangeListener(listener);
        this.titleChangeSupport.addPropertyChangeListener(listener);
        this.scheduleChangeSupport.addPropertyChangeListener(listener);
        this.max_peopleChangeSupport.addPropertyChangeListener(listener);
        this.min_peopleChangeSupport.addPropertyChangeListener(listener);
        this.min_price_peopleChangeSupport.addPropertyChangeListener(listener);
        this.max_priceChangeSupport.addPropertyChangeListener(listener);
        this.actual_priceChangeSupport.addPropertyChangeListener(listener);
        this.min_priceChangeSupport.addPropertyChangeListener(listener);
        this.prices_arrayChangeSupport.addPropertyChangeListener(listener);
        this.menu_idChangeSupport.addPropertyChangeListener(listener);
        this.participantsChangeSupport.addPropertyChangeListener(listener);
        this.menuChangeSupport.addPropertyChangeListener(listener);
        this.restaurantChangeSupport.addPropertyChangeListener(listener);
        this.categoriesChangeSupport.addPropertyChangeListener(listener);
        this.urlImageChangeSupport.addPropertyChangeListener(listener);
        this.urlImageThumbChangeSupport.addPropertyChangeListener(listener);
        this.urlImageMediumChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.idChangeSupport.removePropertyChangeListener(listener);
        this.titleChangeSupport.removePropertyChangeListener(listener);
        this.scheduleChangeSupport.removePropertyChangeListener(listener);
        this.max_peopleChangeSupport.removePropertyChangeListener(listener);
        this.min_peopleChangeSupport.removePropertyChangeListener(listener);
        this.min_price_peopleChangeSupport.removePropertyChangeListener(listener);
        this.max_priceChangeSupport.removePropertyChangeListener(listener);
        this.actual_priceChangeSupport.removePropertyChangeListener(listener);
        this.min_priceChangeSupport.removePropertyChangeListener(listener);
        this.prices_arrayChangeSupport.removePropertyChangeListener(listener);
        this.menu_idChangeSupport.removePropertyChangeListener(listener);
        this.participantsChangeSupport.removePropertyChangeListener(listener);
        this.menuChangeSupport.removePropertyChangeListener(listener);
        this.restaurantChangeSupport.removePropertyChangeListener(listener);
        this.categoriesChangeSupport.removePropertyChangeListener(listener);
        this.urlImageChangeSupport.removePropertyChangeListener(listener);
        this.urlImageThumbChangeSupport.removePropertyChangeListener(listener);
        this.urlImageMediumChangeSupport.removePropertyChangeListener(listener);
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        String oldValue = this.urlImage;
        this.urlImage = urlImage;
        this.urlImageChangeSupport.firePropertyChange("urlImage",oldValue, urlImage);
    }

    public String getUrlImageThumb() {
        return urlImageThumb;
    }

    public void setUrlImageThumb(String urlImageThumb) {
        String oldValue = this.urlImageThumb;
        this.urlImageThumb = urlImageThumb;
        this.urlImageThumbChangeSupport.firePropertyChange("urlImageThumb",oldValue, urlImageThumb);
    }

    public String getUrlImageMedium() {
        return urlImageMedium;
    }

    public void setUrlImageMedium(String urlImageMedium) {
        String oldValue = this.urlImageMedium;
        this.urlImageMedium = urlImageMedium;
        this.urlImageMediumChangeSupport.firePropertyChange("urlImageMedium",oldValue, urlImageMedium);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        ArrayList<Category> oldValue = this.categories;
        this.categories = categories;
        this.categoriesChangeSupport.firePropertyChange("categories",oldValue, categories);
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        Menu oldValue = this.menu;
        this.menu = menu;
        this.menuChangeSupport.firePropertyChange("menu",oldValue, menu);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        Restaurant oldValue = this.restaurant;
        this.restaurant = restaurant;
        this.restaurantChangeSupport.firePropertyChange("restaurant",oldValue, restaurant);
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        int oldValue = this.participants;
        this.participants = participants;
        this.participantsChangeSupport.firePropertyChange("participants",oldValue, participants);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        int oldValue = this.id;
        this.id = id;
        this.idChangeSupport.firePropertyChange("id",oldValue, id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String oldValue = this.title;
        this.title = title;
        this.titleChangeSupport.firePropertyChange("title",oldValue, title);
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        Date oldValue = this.schedule;
        this.schedule = schedule;
        this.scheduleChangeSupport.firePropertyChange("schedule",oldValue, schedule);
    }

    public int getMaxPeople() {
        return max_people;
    }

    public void setMaxPeople(int max_people) {
        int oldValue = this.max_people;
        this.max_people = max_people;
        this.max_peopleChangeSupport.firePropertyChange("max_people",oldValue, max_people);
    }

    public int getMinPeople() {
        return min_people;
    }

    public void setMinPeople(int min_people) {
        int oldValue = this.min_people;
        this.min_people = min_people;
        this.min_peopleChangeSupport.firePropertyChange("min_people",oldValue, min_people);
    }

    public int getMinPricePeople() {
        return min_price_people;
    }

    public void setMinPricePeople(int min_price_people) {
        int oldValue = this.min_price_people;
        this.min_price_people = min_price_people;
        this.min_price_peopleChangeSupport.firePropertyChange("min_price_people",oldValue, min_price_people);
    }

    public double getMaxPrice() {
        return max_price;
    }

    public void setMaxPrice(double max_price) {
        double oldValue = this.max_price;
        this.max_price = max_price;
        this.max_priceChangeSupport.firePropertyChange("max_price",oldValue, max_price);
    }

    public double getActualPrice() {
        return actual_price;
    }

    public void setActualPrice(double actual_price) {
        double oldValue = this.actual_price;
        this.actual_price = actual_price;
        this.actual_priceChangeSupport.firePropertyChange("actual_price",oldValue, actual_price);
    }

    public double getMinPrice() {
        return min_price;
    }

    public void setMinPrice(double min_price) {
        double oldValue = this.min_price;
        this.min_price = min_price;
        this.min_priceChangeSupport.firePropertyChange("min_price",oldValue, min_price);
    }

    public ArrayList<Double> getPricesArray() {
        return prices_array;
    }

    public void setPricesArray(ArrayList<Double> prices_array) {
        ArrayList<Double> oldValue = this.prices_array;
        this.prices_array = prices_array;
        this.prices_arrayChangeSupport.firePropertyChange("prices_array",oldValue, prices_array);
    }

    public int getMenuId() {
        return menu_id;
    }

    public void setMenuId(int menu_id) {
        int oldValue = this.menu_id;
        this.menu_id = menu_id;
        this.menu_idChangeSupport.firePropertyChange("menu_id",oldValue, menu_id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return getId() == event.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
