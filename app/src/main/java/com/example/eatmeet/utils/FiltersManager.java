package com.example.eatmeet.utils;

import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This class saves the state of filters applied to events search
 * Created by umberto on 08/08/16.
 */
public class FiltersManager {

    // VARIABILI PER ORDINAMENTO
    private String orderByField;
    private String orderByDirection;

    // VARIABILI PER FILTRI
    private List<Category> selectedCategories;
    private List<Restaurant> selectedRestaurants;
    private Date minDate;
    private Date maxDate;
    private Double minPrice;
    private Double maxPrice;
    private Double minActualSale;
    private Double maxActualSale;
    private Integer minPeople;
    private Integer maxPeople;

    public FiltersManager() {
        selectedCategories = new ArrayList<>();
        selectedRestaurants = new ArrayList<>();
    }

    // SEZIONE ORDINAMENTO
    public void setOrderBy(String field, String direction) {
        orderByField = field;
        orderByDirection = direction;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public String getOrderByDirection() {
        return orderByDirection;
    }

    public boolean isOrderSet() {
        if(getOrderByDirection() == null || getOrderByField() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void removeOrder() {
        setOrderBy(null,null);
    }
    // FINE SEZIONE ORDINAMENTO

    // SETTING FILTRI PER CATEGORIA
    public List<Category> getSelectedCategories() {
        return selectedCategories;
    }

    public void addCategory(Category category) {
        selectedCategories.add(category);
    }

    public void removeCategory(Category category) {
        selectedCategories.remove(category);
    }

    public boolean isCategoryEnabled() {
        if(selectedCategories.size()==0) {
            return false;
        } else {
            return true;
        }
    }

    public void removeCategories() {
        selectedCategories.clear();
    }
    // FINE SETTING FILTRI PER CATEGORIA

    // SETTING FILTRI PER RISTORANTE
    public List<Restaurant> getSelectedRestaurants() {
        return selectedRestaurants;
    }

    public void addRestaurant(Restaurant restaurant) {
        selectedRestaurants.add(restaurant);
    }

    public void removeRestaurant(Restaurant restaurant) {
        selectedRestaurants.remove(restaurant);
    }

    public boolean isRestaurantEnabled() {
        if(selectedRestaurants.size()==0) {
            return false;
        } else {
            return true;
        }
    }

    public void removeRestaurants() {
        selectedRestaurants.clear();
    }
    // FINE SETTING FILTRI PER RISTORANTE

    // SETTING FILTRI PER MIN DATE
    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public void removeMinDate() {
        setMinDate(null);
    }

    public boolean isMinDateEnabled() {
        return (getMinDate() != null);
    }
    // FINE SETTING FILTRI PER MIN DATE

    // SETTING FILTRI PER MIN DATE
    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public void removeMaxDate() {
        setMaxDate(null);
    }

    public boolean isMaxDateEnabled() {
        return (getMaxDate() != null);
    }
    // FINE SETTING FILTRI PER MIN DATE

    // SETTING FILTRI MIN PRICE
    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public void removeMinPrice() {
        setMinPrice(null);
    }

    public boolean isMinPriceEnabled() {
        return (getMinPrice()!=null);
    }
    // FINE SETTING FILTRI MIN PRICE

    // SETTING FILTRI MIN PRICE
    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double minPrice) {
        this.maxPrice = minPrice;
    }

    public void removeMaxPrice() {
        setMaxPrice(null);
    }

    public boolean isMaxPriceEnabled() {
        return (getMaxPrice()!=null);
    }
    // FINE SETTING FILTRI MIN PRICE

    // SETTING FILTRI PER MIN ACTUAL PRICE
    public Double getMinActualSale() {
        return minActualSale;
    }

    public void setMinActualSale(Double minActualSale) {
        this.minActualSale = minActualSale;
    }

    public void removeMinActualSale() {
        setMinActualSale(null);
    }

    public boolean isMinActualSaleEnabled() {
        return (getMinActualSale()!=null);
    }
    // FINE SETTING FILTRI PER ACTUAL PRICE

    // SETTING FILTRI PER MAX ACTUAL PRICE
    public Double getMaxActualSale() {
        return maxActualSale;
    }

    public void setMaxActualSale(Double maxActualSale) {
        this.maxActualSale = maxActualSale;
    }

    public void removeMaxActualSale() {
        setMaxActualSale(null);
    }

    public boolean isMaxActualSaleEnabled() {
        return (getMaxActualSale()!=null);
    }
    // FINE SETTING FILTRI PER ACTUAL PRICE

    // SETTING FILTRI PER MIN PEOPLE
    public Integer getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(Integer minPeople) {
        this.minPeople = minPeople;
    }

    public void removeMinPeople() {
        setMinPeople(null);
    }

    public boolean isMinPeopleEnabled() {
        return (getMinPeople()!=null);
    }
    // FINE SETTING FILTRI PER MIN PEOPLE

    // SETTING FILTRI PER MIN PEOPLE
    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public void removeMaxPeople() {
        setMaxPeople(null);
    }

    public boolean isMaxPeopleEnabled() {
        return (getMaxPeople()!=null);
    }
    // FINE SETTING FILTRI PER MIN PEOPLE

    public void resetFilters() {
        removeOrder();
        removeMinDate();
        removeMaxDate();
        removeMinPrice();
        removeMaxPrice();
        removeMinActualSale();
        removeMaxActualSale();
        removeMinPeople();
        removeMaxPeople();
        removeRestaurants();
        removeCategories();
    }

    public JSONObject buildJson() {
        try {
            JSONObject parameters = new JSONObject();
            JSONObject filters = new JSONObject();

            if(isCategoryEnabled()) {
                JSONArray filter = new JSONArray();
                for(Category c : selectedCategories) {
                    filter.put(c.getId());
                }
                filters.put("categories", filter);
            }

            if(isRestaurantEnabled()) {
                JSONArray filter = new JSONArray();
                for(Restaurant r : selectedRestaurants) {
                    filter.put(r.getId());
                }
                filters.put("restaurants", filter);
            }

            if(isMinDateEnabled() || isMaxDateEnabled()) {
                JSONObject filter = new JSONObject();
                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
                if(isMinDateEnabled()) {
                    filter.put("start", dateParser.format(this.getMinDate()));
                }
                if(isMaxDateEnabled()) {
                    filter.put("end", dateParser.format(this.getMaxDate()));
                }
                filters.put("date_range", filter);
            }

            if(isMinPriceEnabled() || isMaxPriceEnabled()) {
                JSONObject filter = new JSONObject();
                if(isMinPriceEnabled()) {
                    filter.put("start", getMinPrice());
                }
                if(isMaxPriceEnabled()) {
                    filter.put("end", getMaxPrice());
                }
                filters.put("price_range", filter);
            }

            if(isMinActualSaleEnabled() || isMaxActualSaleEnabled()) {
                JSONObject filter = new JSONObject();
                if(isMinActualSaleEnabled()) {
                    filter.put("start", getMinActualSale());
                }
                if(isMaxActualSaleEnabled()) {
                    filter.put("end", getMaxActualSale());
                }
                filters.put("actual_sale_range", filter);
            }

            if(isMinPeopleEnabled() || isMaxPeopleEnabled()) {
                JSONObject filter = new JSONObject();
                if(isMinPeopleEnabled()) {
                    filter.put("start", getMinActualSale());
                }
                if(isMaxPeopleEnabled()) {
                    filter.put("end", getMaxActualSale());
                }
                filters.put("participants_range", filter);
            }

            if(isOrderSet()) {
                JSONObject order = new JSONObject();
                order.put("field", getOrderByField());
                order.put("direction", getOrderByDirection());
                parameters.put("order", order);
            }
            parameters.put("filters", filters);

            System.out.println(parameters);
            return parameters;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
