package com.example.Downloader.client;

import com.example.Downloader.data.Response;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.example.Downloader.util.Constants.DOWNLOAD_URL;

public interface QueryApiInterface {

    @POST(DOWNLOAD_URL)
    Call<Response> getResult(@Body Map<String, String> query);
}
