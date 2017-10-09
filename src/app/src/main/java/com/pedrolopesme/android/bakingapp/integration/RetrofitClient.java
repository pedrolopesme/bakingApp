package com.pedrolopesme.android.bakingapp.integration;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit Client
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;

    /**
     * Creates an retrofit client or just return the last client created.
     *
     * @param baseUrl reference
     * @return retrofit client
     */
    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
