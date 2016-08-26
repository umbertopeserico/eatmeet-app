package com.example.eatmeet.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public class Event extends AbstractEntity implements Serializable{
    private String title;
    private Date schedule;
    private Integer maxPeople;
    private Integer minPeople;
    private Integer minPricePeople;
    private Double maxPrice;
    private Double actualPrice;
    private Double minPrice;
    private List<Double> pricesArray;
    private Integer menuId;
    private Integer participantsCount;
    private List<Photo> photos;
    private List<User> participants;

    private Menu menu;
    private Restaurant restaurant;
    private List<Category> categories;
    private List<EventParticipation> eventParticipations;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        List<Category> oldValue = this.categories;
        this.categories = categories;
        this.getCs().firePropertyChange("categories",oldValue, categories);
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        Menu oldValue = this.menu;
        this.menu = menu;
        this.getCs().firePropertyChange("menu",oldValue, menu);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        Restaurant oldValue = this.restaurant;
        this.restaurant = restaurant;
        this.getCs().firePropertyChange("restaurant",oldValue, restaurant);
    }

    public Integer getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(Integer participants_count) {
        Integer oldValue = this.participantsCount;
        this.participantsCount = participants_count;
        this.getCs().firePropertyChange("participants_count",oldValue, participants_count);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String oldValue = this.title;
        this.title = title;
        this.getCs().firePropertyChange("title",oldValue, title);
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        Date oldValue = this.schedule;
        this.schedule = schedule;
        this.getCs().firePropertyChange("schedule",oldValue, schedule);
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer max_people) {
        Integer oldValue = this.maxPeople;
        this.maxPeople = max_people;
        this.getCs().firePropertyChange("maxPeople",oldValue, max_people);
    }

    public Integer getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(Integer min_people) {
        Integer oldValue = this.minPeople;
        this.minPeople = min_people;
        this.getCs().firePropertyChange("minPeople",oldValue, min_people);
    }

    public Integer getMinPricePeople() {
        return minPricePeople;
    }

    public void setMinPricePeople(Integer min_price_people) {
        Integer oldValue = this.minPricePeople;
        this.minPricePeople = min_price_people;
        this.getCs().firePropertyChange("minPricePeople",oldValue, min_price_people);
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double max_price) {
        Double oldValue = this.maxPrice;
        this.maxPrice = max_price;
        this.getCs().firePropertyChange("maxPrice",oldValue, max_price);
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actual_price) {
        Double oldValue = this.actualPrice;
        this.actualPrice = actual_price;
        this.getCs().firePropertyChange("actual_price",oldValue, actual_price);
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double min_price) {
        Double oldValue = this.minPrice;
        this.minPrice = min_price;
        this.getCs().firePropertyChange("minPrice",oldValue, min_price);
    }

    public List<Double> getPricesArray() {
        return pricesArray;
    }

    public void setPricesArray(List<Double> prices_array) {
        List<Double> oldValue = this.pricesArray;
        this.pricesArray = prices_array;
        this.getCs().firePropertyChange("pricesArray",oldValue, prices_array);
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menu_id) {
        Integer oldValue = this.menuId;
        this.menuId = menu_id;
        this.getCs().firePropertyChange("menuId",oldValue, menu_id);
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        List<Photo> oldValue = this.photos;
        this.photos = photos;
        this.getCs().firePropertyChange("photos", oldValue, photos);
    }

    public List<EventParticipation> getEventParticipations() {
        return eventParticipations;
    }

    public void setEventParticipations(List<EventParticipation> eventParticipations) {
        List<EventParticipation> oldValue = eventParticipations;
        this.eventParticipations = eventParticipations;
        this.getCs().firePropertyChange("eventParticipations", oldValue, this.eventParticipations);
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
