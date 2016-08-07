package com.example.eatmeet.observablearraylist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by umberto on 11/07/16.
 */
public class ObservableArrayList<T> extends ArrayList {

    private List<OnAddListener> addListeners = new ArrayList<>();
    private List<OnRemoveListener> removeListeners = new ArrayList<>();

    @Override
    public boolean add(Object object) {
        if(super.add(object)) {
            this.notifyAdd(object);
            return true;
        } else {
            return false;
        }
    }



    @Override
    public void add(int index, Object object) {
        super.add(index, object);
        this.notifyAdd(object);
    }

    @Override
    public Object remove(int index) {
        Object object = super.remove(index);
        notifyRemove(object);
        return object;
    }

    @Override
    public boolean remove(Object object) {
        if(super.remove(object)) {
            this.notifyRemove(object);
            return true;
        } else {
            return false;
        }
    }

    public void setOnAddListener(OnAddListener listener) {
        this.addListeners.add(listener);
    }

    public void setOnRemoveListener(OnRemoveListener listener) {
        this.removeListeners.add(listener);
    }

    private void notifyAdd(Object item) {
        for(OnAddListener listener : addListeners) {
            listener.onAddEvent(item);
        }
    }

    private void notifyRemove(Object item) {
        for(OnRemoveListener listener : removeListeners) {
            listener.onRemoveEvent(item);
        }
    }

}
