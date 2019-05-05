package com.example.Downloader.helper;

import android.content.Context;
import android.util.Log;

import com.example.Downloader.app.DownloaderApplication;
import com.example.Downloader.client.ApiClient;
import com.example.Downloader.client.QueryApiInterface;
import com.example.Downloader.data.Response;
import com.example.Downloader.util.LogUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;

public class DownloaderClient {

    private static final String TAG = "DownloaderClient";

    private static DownloaderClient mInstance;
    private DownloaderClientListener listener;
    private Context context;

    private DownloaderClient(Context context) {
        this.context = context;
    }

    public static synchronized DownloaderClient getInstance() {
        if(Objects.isNull(mInstance))
            mInstance = new DownloaderClient(DownloaderApplication.getInstance());
        return mInstance;
    }

    public void register(DownloaderClientListener listener) {
        if(Objects.nonNull(mInstance))
            mInstance.setListener(listener);
    }

    public void deregister() {
        if(Objects.nonNull(mInstance))
            mInstance.setListener(null);
    }

    private void setListener(DownloaderClientListener listener) {
        this.listener = listener;
    }

    public void requestDownload(final String url, int parts) {

        Map<String, String> queries = new HashMap<String, String>();
        queries.put("url", url);
        queries.put("parts",String.valueOf(parts));

        QueryApiInterface queryApiInterface = ApiClient.getApiClient().create(QueryApiInterface.class);
        Call<Response> call = queryApiInterface.getResult(queries);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                LogUtil.warn(TAG, response.body().toString());
                if(response.code() == 200) {
                    if(Objects.nonNull(listener))
                        listener.updateFileInfo(response.body());
                } else {
                    try {
                        LogUtil.error(TAG, "Error : "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(Objects.nonNull(listener))
                        listener.updateFileInfo(null);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                if(Objects.nonNull(listener))
                    listener.updateFileInfo(null);
                LogUtil.error(TAG, "Server Error : "+t.getMessage());
            }
        });
    }
}
