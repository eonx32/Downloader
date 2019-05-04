package com.example.Downloader.helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Downloader.DownloaderApplication;
import com.example.Downloader.data.File;
import com.example.Downloader.util.LogUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.Downloader.util.Constants.DOWNLOAD_URL;
import static com.example.Downloader.util.FileUtil.getFileFromJSON;

public class DownloaderClient {

    private static final String TAG = "DownloaderClient";

    private static DownloaderClient mInstance;
    private DownloaderClientListener listener;
    private Context context;
    private RequestQueue mRequestQueue;

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

    public RequestQueue getRequestQueue() {
        if (Objects.isNull(mRequestQueue)) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public void requestDownload(final String url,
                                final String name, final int parts) {
        StringRequest request
                = new StringRequest(
                Request.Method.POST,
                DOWNLOAD_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        LogUtil.info(TAG, response);
                        try {
                            File file = getFileFromJSON(response, name);
                            listener.updateFileInfo(file);
                            LogUtil.error(TAG, "Success");
                            return ;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            LogUtil.error(TAG, "Exception");
                            listener.updateFileInfo(null);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        LogUtil.error(TAG, "Error: "
                                + error.getMessage());
                        LogUtil.error(TAG, "Failure");
                        listener.updateFileInfo(null);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put("url", url);
                params.put("parts", String.valueOf(parts));

                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        getRequestQueue().add(request);
    }
}
