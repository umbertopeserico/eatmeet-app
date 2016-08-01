package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by umberto on 27/07/16.
 */
public class Photo extends AbstractEntity {

    private String image;
    private String image_medium;
    private String image_thumb;
    private PropertyChangeSupport cs = new PropertyChangeSupport(this);

    @Override
    protected void setPropertyChangeListener(PropertyChangeListener listener) {
        this.cs.addPropertyChangeListener(listener);
    }

    @Override
    protected void unsetPropertyChangeListener(PropertyChangeListener listener) {
        this.cs.removePropertyChangeListener(listener);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        String oldValue = this.image;
        this.image = image;
        this.cs.firePropertyChange("image",oldValue, this.image);
    }

    public String getImageMedium() {
        return image_medium;
    }

    public void setImageMedium(String imageMedium) {
        String oldValue = this.image_medium;
        this.image_medium = imageMedium;
        this.cs.firePropertyChange("image_medium", oldValue, this.image_medium);
    }

    public String getImageThumb() {
        return image_thumb;
    }

    public void setImageThumb(String imageThumb) {
        String oldValue = this.image_thumb;
        this.image_thumb = imageThumb;
        this.cs.firePropertyChange("image_thumb", oldValue, this.image_thumb);
    }
}
