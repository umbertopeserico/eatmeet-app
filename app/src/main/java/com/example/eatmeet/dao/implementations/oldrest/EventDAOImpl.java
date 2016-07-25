package com.example.eatmeet.dao.implementations.oldrest;

import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.Menu;
import com.example.eatmeet.entities.Restaurant;
import com.example.eatmeet.utils.Configs;
import com.example.eatmeet.utils.Connection;
import com.example.eatmeet.utils.Notificable;
import com.example.eatmeet.utils.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by sofia on 08/06/2016.
 */
public class EventDAOImpl implements EventDAO {

    Notificable mNotificable;

    public EventDAOImpl(Notificable notificable) {
        mNotificable = notificable;
    }

    @Override
    public  Event getEventById(int eventId){
        final Event myNewEvent = new Event();
        new Connection() {
            @Override public void onPostExecute(String result) {
                try {
                    JSONObject myEventJson = new JSONObject(result);
                    myNewEvent.setId(myEventJson.getInt("id"));
                    myNewEvent.setTitle(myEventJson.getString("title"));

                    SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    parserSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String string = myEventJson.getString("schedule");
                    Date scheduleDate = null;
                    try {
                        scheduleDate = parserSDF.parse(string);
                        myNewEvent.setSchedule(scheduleDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    myNewEvent.setMaxPeople(myEventJson.getInt("max_people"));
                    myNewEvent.setMinPeople(myEventJson.getInt("min_people"));
                    myNewEvent.setMinPricePeople(myEventJson.getInt("people_min_price"));
                    myNewEvent.setMaxPrice(Double.parseDouble(myEventJson.getString("max_price")));
                    myNewEvent.setMinPrice(Double.parseDouble(myEventJson.getString("min_price")));
                    myNewEvent.setActualPrice(Double.parseDouble(myEventJson.getString("actual_price")));
                    myNewEvent.setParticipants(myEventJson.getInt("participants_count"));
                    myNewEvent.setMenuId(myEventJson.getInt("menu_id"));
                    JSONArray prices_json_array = myEventJson.getJSONArray("prices_array");
                    ArrayList<Double> prices_array = new ArrayList<>();
                    for(int i = 0; i < prices_json_array.length(); i++){
                        prices_array.add(Double.parseDouble(prices_json_array.getString(i)));
                    }
                    myNewEvent.setPricesArray(prices_array);

                    JSONObject restaurantJson = myEventJson.getJSONObject("restaurant");
                    Restaurant restaurantClass = new Restaurant();
                    restaurantClass.setName(restaurantJson.getString("name"));
                    restaurantClass.setStreet(restaurantJson.getString("street"));
                    restaurantClass.setCity(restaurantJson.getString("city"));
                    restaurantClass.setZipCode(restaurantJson.getString("zip_code"));
                    restaurantClass.setProvince(restaurantJson.getString("province"));
                    myNewEvent.setRestaurant(restaurantClass);

                    JSONObject menuJson = myEventJson.getJSONObject("menu");
                    Menu menuClass = new Menu();
                    menuClass.setTextMenu(menuJson.getString("text_menu"));
                    myNewEvent.setMenu(menuClass);

                    JSONArray categoriesJson = myEventJson.getJSONArray("categories");
                    ArrayList<Category> categoriesClass = new ArrayList<>();
                    for(int i = 0; i < categoriesJson.length(); i++){
                        Category c = new Category();
                        c.setName(categoriesJson.getJSONObject(i).getString("name"));
                        categoriesClass.add(c);
                    }
                    myNewEvent.setCategories(categoriesClass);
                    myNewEvent.setUrlImage(myEventJson.getJSONArray("photos").getJSONObject(0).getString("image"));
                    myNewEvent.setUrlImageMedium(myEventJson.getJSONArray("photos").getJSONObject(0).getString("image_medium"));
                    myNewEvent.setUrlImageThumb(myEventJson.getJSONArray("photos").getJSONObject(0).getString("image_thumb"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //}.execute(new Configs().getBackendUrl() + "api/events/" + eventId);
        }.execute(new Configs().getBackendUrl() + "api/events/" + eventId);

        return myNewEvent;
    }

    @Override
    public List<Event> getEvents(JSONObject parameters) {
        final List<Event> allEvents = new ArrayList<Event>();
        if(parameters!=null) {
            new Post() {
                @Override
                public void onPostExecute(String result) {
                    try {
                        if(result != null) {
                            JSONObject obj = new JSONObject(result);
                            JSONArray arr = obj.getJSONArray("events");
                            for (int j = 0; j < arr.length(); j++) {
                                /*
                                String title = arr.getJSONObject(i).getString("title");
                                int id = arr.getJSONObject(i).getInt("id");
                                Event newEvent = new Event();
                                newEvent.setId(id);
                                newEvent.setTitle(title);
                                allEvents.add(newEvent);
                                mNotificable.sendNotify();
                                */
                                Event myNewEvent = new Event();
                                JSONObject myEventJson = arr.getJSONObject(j);
                                try {
                                    myNewEvent.setId(myEventJson.getInt("id"));
                                    myNewEvent.setTitle(myEventJson.getString("title"));

                                    SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                    parserSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
                                    String string = myEventJson.getString("schedule");
                                    Date scheduleDate = null;
                                    try {
                                        scheduleDate = parserSDF.parse(string);
                                        myNewEvent.setSchedule(scheduleDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    myNewEvent.setMaxPeople(myEventJson.getInt("max_people"));
                                    myNewEvent.setMinPeople(myEventJson.getInt("min_people"));
                                    myNewEvent.setMinPricePeople(myEventJson.getInt("people_min_price"));
                                    myNewEvent.setMaxPrice(Double.parseDouble(myEventJson.getString("max_price")));
                                    myNewEvent.setMinPrice(Double.parseDouble(myEventJson.getString("min_price")));
                                    myNewEvent.setActualPrice(Double.parseDouble(myEventJson.getString("actual_price")));
                                    myNewEvent.setParticipants(myEventJson.getInt("participants_count"));
                                    myNewEvent.setMenuId(myEventJson.getInt("menu_id"));
                                    JSONArray prices_json_array = myEventJson.getJSONArray("prices_array");
                                    ArrayList<Double> prices_array = new ArrayList<>();
                                    for(int i = 0; i < prices_json_array.length(); i++){
                                        prices_array.add(Double.parseDouble(prices_json_array.getString(i)));
                                    }
                                    myNewEvent.setPricesArray(prices_array);

                                    JSONObject restaurantJson = myEventJson.getJSONObject("restaurant");
                                    Restaurant restaurantClass = new Restaurant();
                                    restaurantClass.setName(restaurantJson.getString("name"));
                                    restaurantClass.setStreet(restaurantJson.getString("street"));
                                    restaurantClass.setCity(restaurantJson.getString("city"));
                                    restaurantClass.setZipCode(restaurantJson.getString("zip_code"));
                                    restaurantClass.setProvince(restaurantJson.getString("province"));
                                    myNewEvent.setRestaurant(restaurantClass);

                                    JSONObject menuJson = myEventJson.getJSONObject("menu");
                                    Menu menuClass = new Menu();
                                    menuClass.setTextMenu(menuJson.getString("text_menu"));
                                    myNewEvent.setMenu(menuClass);

                                    JSONArray categoriesJson = myEventJson.getJSONArray("categories");
                                    ArrayList<Category> categoriesClass = new ArrayList<>();
                                    for(int i = 0; i < categoriesJson.length(); i++){
                                        Category c = new Category();
                                        c.setName(categoriesJson.getJSONObject(i).getString("name"));
                                        categoriesClass.add(c);
                                    }
                                    myNewEvent.setCategories(categoriesClass);
                                    myNewEvent.setUrlImage(myEventJson.getJSONArray("photos").getJSONObject(0).getString("image"));
                                    myNewEvent.setUrlImageMedium(myEventJson.getJSONArray("photos").getJSONObject(0).getString("image_medium"));
                                    myNewEvent.setUrlImageThumb(myEventJson.getJSONArray("photos").getJSONObject(0).getString("image_thumb"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                allEvents.add(myNewEvent);
                                mNotificable.sendNotify();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute(new Configs().getBackendUrl() + "api/events/search",parameters);
        }
        return allEvents;
    }
}
