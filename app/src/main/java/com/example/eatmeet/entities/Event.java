package com.example.eatmeet.entities;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    private Double actual_price;
    private Double minPrice;
    private List<Double> pricesArray;
    private Integer menuId;
    private Integer participants_count;
    private List<Photo> photos;
    private String urlImage;
    private String urlImageThumb;
    private String urlImageMedium;

    private Menu menu;
    private Restaurant restaurant;
    private List<Category> categories;
    private List<EventParticipation> eventParticipations;

    private final PropertyChangeSupport cs = new PropertyChangeSupport(this);
    private final PropertyChangeSupport titleChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport scheduleChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport maxPeopleChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport minPeopleChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport minPricePeopleChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport maxPriceChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport actualPriceChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport minPriceChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport pricesArrayChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport menuIdChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport participantsChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport menuChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport restaurantChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport categoriesChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport urlImageChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport urlImageThumbChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport urlImageMediumChangeSupport = new PropertyChangeSupport(this);

    public void setPropertyChangeListener(PropertyChangeListener listener) {
        this.titleChangeSupport.addPropertyChangeListener(listener);
        this.scheduleChangeSupport.addPropertyChangeListener(listener);
        this.maxPeopleChangeSupport.addPropertyChangeListener(listener);
        this.minPeopleChangeSupport.addPropertyChangeListener(listener);
        this.minPricePeopleChangeSupport.addPropertyChangeListener(listener);
        this.maxPriceChangeSupport.addPropertyChangeListener(listener);
        this.actualPriceChangeSupport.addPropertyChangeListener(listener);
        this.minPriceChangeSupport.addPropertyChangeListener(listener);
        this.pricesArrayChangeSupport.addPropertyChangeListener(listener);
        this.menuIdChangeSupport.addPropertyChangeListener(listener);
        this.participantsChangeSupport.addPropertyChangeListener(listener);
        this.menuChangeSupport.addPropertyChangeListener(listener);
        this.restaurantChangeSupport.addPropertyChangeListener(listener);
        this.categoriesChangeSupport.addPropertyChangeListener(listener);
        this.urlImageChangeSupport.addPropertyChangeListener(listener);
        this.urlImageThumbChangeSupport.addPropertyChangeListener(listener);
        this.urlImageMediumChangeSupport.addPropertyChangeListener(listener);
        this.cs.addPropertyChangeListener(listener);
    }

    public void unsetPropertyChangeListener(PropertyChangeListener listener) {
        this.titleChangeSupport.removePropertyChangeListener(listener);
        this.scheduleChangeSupport.removePropertyChangeListener(listener);
        this.maxPeopleChangeSupport.removePropertyChangeListener(listener);
        this.minPeopleChangeSupport.removePropertyChangeListener(listener);
        this.minPricePeopleChangeSupport.removePropertyChangeListener(listener);
        this.maxPriceChangeSupport.removePropertyChangeListener(listener);
        this.actualPriceChangeSupport.removePropertyChangeListener(listener);
        this.minPriceChangeSupport.removePropertyChangeListener(listener);
        this.pricesArrayChangeSupport.removePropertyChangeListener(listener);
        this.menuIdChangeSupport.removePropertyChangeListener(listener);
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        List<Category> oldValue = this.categories;
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

    public Integer getParticipantsCount() {
        return participants_count;
    }

    public void setParticipantsCount(Integer participants_count) {
        Integer oldValue = this.participants_count;
        this.participants_count = participants_count;
        this.participantsChangeSupport.firePropertyChange("participants_count",oldValue, participants_count);
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

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer max_people) {
        Integer oldValue = this.maxPeople;
        this.maxPeople = max_people;
        this.maxPeopleChangeSupport.firePropertyChange("maxPeople",oldValue, max_people);
    }

    public Integer getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(Integer min_people) {
        Integer oldValue = this.minPeople;
        this.minPeople = min_people;
        this.minPeopleChangeSupport.firePropertyChange("minPeople",oldValue, min_people);
    }

    public Integer getMinPricePeople() {
        return minPricePeople;
    }

    public void setMinPricePeople(Integer min_price_people) {
        Integer oldValue = this.minPricePeople;
        this.minPricePeople = min_price_people;
        this.minPricePeopleChangeSupport.firePropertyChange("minPricePeople",oldValue, min_price_people);
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double max_price) {
        Double oldValue = this.maxPrice;
        this.maxPrice = max_price;
        this.maxPriceChangeSupport.firePropertyChange("maxPrice",oldValue, max_price);
    }

    public Double getActualPrice() {
        return actual_price;
    }

    public void setActualPrice(Double actual_price) {
        Double oldValue = this.actual_price;
        this.actual_price = actual_price;
        this.actualPriceChangeSupport.firePropertyChange("actual_price",oldValue, actual_price);
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double min_price) {
        Double oldValue = this.minPrice;
        this.minPrice = min_price;
        this.minPriceChangeSupport.firePropertyChange("minPrice",oldValue, min_price);
    }

    public List<Double> getPricesArray() {
        return pricesArray;
    }

    public void setPricesArray(List<Double> prices_array) {
        List<Double> oldValue = this.pricesArray;
        this.pricesArray = prices_array;
        this.pricesArrayChangeSupport.firePropertyChange("pricesArray",oldValue, prices_array);
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menu_id) {
        Integer oldValue = this.menuId;
        this.menuId = menu_id;
        this.menuIdChangeSupport.firePropertyChange("menuId",oldValue, menu_id);
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        List<Photo> oldValue = this.photos;
        this.photos = photos;
        this.cs.firePropertyChange("photos", oldValue, photos);
    }

    public List<EventParticipation> getEventParticipations() {
        return eventParticipations;
    }

    public void setEventParticipations(List<EventParticipation> eventParticipations) {
        List<EventParticipation> oldValue = eventParticipations;
        this.eventParticipations = eventParticipations;
        this.cs.firePropertyChange("eventParticipations", oldValue, this.eventParticipations);
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
