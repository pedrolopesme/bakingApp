package com.pedrolopesme.android.bakingapp.integration;

import com.pedrolopesme.android.bakingapp.integration.api.RecipesAPIService;

/**
 * API Service Factory
 */
public interface APIServiceFactory {

    RecipesAPIService getRecipesAPIService();

}
