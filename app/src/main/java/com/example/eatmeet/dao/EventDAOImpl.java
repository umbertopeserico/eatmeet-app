package com.example.eatmeet.dao;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.eatmeet.R;
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
import java.util.HashMap;
import java.util.List;

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
                /*
                String restaurantName = "";
                String restaurantStreet = "";
                String restaurantCity = "";
                String restaurantZipCode = "";
                String restaurantProvince = "";
                String menuText = "";
                ArrayList<String> categoriesNames = new ArrayList<>();
                */
                try {
                    JSONObject myEventJson = new JSONObject(result);
                    myNewEvent.setId(myEventJson.getInt("id"));
                    myNewEvent.setTitle(myEventJson.getString("title"));

                    SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
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

        /*
        Event event0 = new Event();
        event0.setId(0);
        event0.setTitle("Pizza a volontà");
        event0.setSchedule(new Date());
        event0.setMaxPeople(30);
        event0.setMinPeople(10);
        event0.setMinPricePeople(20);
        event0.setMaxPrice(18.00);
        event0.setActualPrice(16.00);
        event0.setMinPrice(12.00);

        Event event1 = new Event();
        event1.setId(1);
        event1.setTitle("Sushi e sashimi");
        event1.setSchedule(new Date());
        event0.setMaxPeople(40);
        event0.setMinPeople(12);
        event0.setMinPricePeople(30);
        event0.setMaxPrice(40.00);
        event0.setActualPrice(35.00);
        event0.setMinPrice(20.00);

        Event event2 = new Event();
        event2.setId(2);
        event2.setTitle("Vino per tutti!");
        event2.setSchedule(new Date());
        event0.setMaxPeople(30);
        event0.setMinPeople(10);
        event0.setMinPricePeople(20);
        event0.setMaxPrice(18.00);
        event0.setActualPrice(16.00);
        event0.setMinPrice(12.00);

        Event event3 = new Event();
        event3.setId(3);
        event3.setTitle("1000 varietà di legumi BIO");
        event3.setSchedule(new Date());
        event0.setMaxPeople(30);
        event0.setMinPeople(10);
        event0.setMinPricePeople(20);
        event0.setMaxPrice(18.00);
        event0.setActualPrice(16.00);
        event0.setMinPrice(12.00);

        */
        final List<Event> allEvents = new ArrayList<Event>();

        if(parameters!=null) {
            new Post() {
                @Override
                public void onPostExecute(String result) {
                    try {
                        if(result != null) {
                            JSONObject obj = new JSONObject(result);
                            JSONArray arr = obj.getJSONArray("events");
                            for (int i = 0; i < arr.length(); i++) {
                                String title = arr.getJSONObject(i).getString("title");
                                int id = arr.getJSONObject(i).getInt("id");
                                Event newEvent = new Event();
                                newEvent.setId(id);
                                newEvent.setTitle(title);
                                allEvents.add(newEvent);
                                mNotificable.sendNotify();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute(new Configs().getBackendUrl() + "api/events/search",parameters);
        }
        /*
        allEvents.add(event0);
        allEvents.add(event1);
        allEvents.add(event2);
        allEvents.add(event3);
        */
        return allEvents;
    }
}
