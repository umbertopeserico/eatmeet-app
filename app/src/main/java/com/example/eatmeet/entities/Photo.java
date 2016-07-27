package com.example.eatmeet.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by umberto on 27/07/16.
 */
public class Photo extends AbstractEntity {

    private String image;
    private String imageMedium;
    private String imageThumb;
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
        return imageMedium;
    }

    public void setImageMedium(String imageMedium) {
        String oldValue = this.imageMedium;
        this.imageMedium = imageMedium;
        this.cs.firePropertyChange("imageMedium", oldValue, this.imageMedium);
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        String oldValue = this.imageThumb;
        this.imageThumb = imageThumb;
        this.cs.firePropertyChange("imageThumb", oldValue, this.imageThumb);
    }
}
