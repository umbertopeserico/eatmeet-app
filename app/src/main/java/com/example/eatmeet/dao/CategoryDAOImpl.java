package com.example.eatmeet.dao;

import com.example.eatmeet.entities.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public List<Category> getCategories() {

        Category pizza = new Category();
        pizza.setId(0);
        pizza.setName("pizza");

        Category sushi = new Category();
        sushi.setId(1);
        sushi.setName("sushi");

        Category vino = new Category();
        vino.setId(2);
        vino.setName("vino");

        Category biologico = new Category();
        biologico.setId(3);
        biologico.setName("biologico");

        List<Category> allCategories = new ArrayList<Category>();

        allCategories.add(pizza);
        allCategories.add(sushi);
        allCategories.add(vino);
        allCategories.add(biologico);

        return allCategories;
    }
}
