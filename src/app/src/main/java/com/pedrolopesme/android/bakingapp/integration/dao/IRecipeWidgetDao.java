package com.pedrolopesme.android.bakingapp.integration.dao;


import com.pedrolopesme.android.bakingapp.models.Recipe;

public interface IRecipeWidgetDao {

    void insert(final Recipe recipe);

    void delete();

    Recipe getRecipeWidget();

}
