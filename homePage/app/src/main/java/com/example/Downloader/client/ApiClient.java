package com.example.Downloader.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.Downloader.util.Constants.SERVER_ADDRESS;

public class ApiClient {

    private static Retrofit apiClient;
    private static final String BASE_URL = SERVER_ADDRESS;

    public static Retrofit getApiClient() {
        if(apiClient == null) {
            apiClient = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return apiClient;
    }
}
