package com.example.eatmeet.dao;

import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.Notificable;

import java.util.ArrayList;
import java.util.Date;
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
    public List<Event> getEvents() {

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

        List<Event> allEvents = new ArrayList<Event>();

        allEvents.add(event0);
        allEvents.add(event1);
        allEvents.add(event2);
        allEvents.add(event3);
        return allEvents;
    }
}
