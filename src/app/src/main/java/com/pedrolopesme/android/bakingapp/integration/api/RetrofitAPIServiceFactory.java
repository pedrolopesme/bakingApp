package com.pedrolopesme.android.bakingapp.integration.api;

import com.pedrolopesme.android.bakingapp.integration.APIServiceFactory;
import com.pedrolopesme.android.bakingapp.integration.RetrofitClient;

/**
 * Retrofit API Service Factory
 */

public class RetrofitAPIServiceFactory extends BaseRetrofitAPIServiceFactory implements APIServiceFactory {

    public RetrofitAPIServiceFactory(String baseURL) {
        super(baseURL);
    }

    @Override
    public RecipesAPIService getRecipesAPIService() {
        return RetrofitClient.getClient(baseURL).create(RecipesAPIService.class);
    }

}
