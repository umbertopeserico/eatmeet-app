package com.example.eatmeet.entities;

/**
 * Created by umberto on 27/07/16.
 */
public class Photo extends AbstractEntity {

    private String image;
    private String imageMedium;
    private String imageThumb;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        String oldValue = this.image;
        this.image = image;
        this.getCs().firePropertyChange("image",oldValue, this.image);
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public void setImageMedium(String imageMedium) {
        String oldValue = this.imageMedium;
        this.imageMedium = imageMedium;
        this.getCs().firePropertyChange("imageMedium", oldValue, this.imageMedium);
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        String oldValue = this.imageThumb;
        this.imageThumb = imageThumb;
        this.getCs().firePropertyChange("imageThumb", oldValue, this.imageThumb);
    }
}
