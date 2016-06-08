package com.example.eatmeet.dao;

import com.example.eatmeet.entities.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public class CategoryDAOImpl implements CategoryDAO {

    public HashMap<String,String> getMeta(){
        HashMap<String,String> meta = new HashMap<>();
        meta.put("events_count","3");
        return null;
    }

    @Override
    public List<Category> getCategories() {

        Category pizza = new Category();
        pizza.setId(0);
        pizza.setMeta(this.getMeta());
        pizza.setName("pizza");

        Category sushi = new Category();
        sushi.setId(1);
        sushi.setMeta(this.getMeta());
        sushi.setName("sushi");

        Category vino = new Category();
        vino.setId(2);
        vino.setMeta(this.getMeta());
        vino.setName("vino");

        Category biologico = new Category();
        biologico.setId(3);
        biologico.setMeta(this.getMeta());
        biologico.setName("biologico");

        List<Category> allCategories = new ArrayList<Category>();

        allCategories.add(pizza);
        allCategories.add(sushi);
        allCategories.add(vino);
        allCategories.add(biologico);

        return allCategories;
    }
}
