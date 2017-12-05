package com.pedrolopesme.android.bakingapp.integration.api;

/**
 * Base Retrofit API Service Factory
 */

public abstract class BaseRetrofitAPIServiceFactory {

    protected String baseURL;

    BaseRetrofitAPIServiceFactory(String baseURL) {
        this.baseURL = baseURL;

    }

}
