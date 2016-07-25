package com.example.eatmeet.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by sofia on 08/06/2016.
 */
public class Category implements Serializable {
    private int id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    private String icon;
    private Map<String,String> meta;

    public Map<String, String> getMeta() {
        return meta;
    }

    public String getIcon(){
        return this.icon;
    }

    public void setIcon(String icon){
        this.icon=icon;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date created_at) {
        this.createdAt = created_at;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updatedAt = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (getId() != category.getId()) return false;
        return getName().equals(category.getName());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getName();
    }
}
