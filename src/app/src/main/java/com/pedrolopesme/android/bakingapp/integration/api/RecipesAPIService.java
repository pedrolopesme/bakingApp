package com.pedrolopesme.android.bakingapp.integration.api;

import com.pedrolopesme.android.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit Recipes API Service
 */
public interface RecipesAPIService {

    @GET("baking.json")
    Call<List<Recipe>> getAll();

}
