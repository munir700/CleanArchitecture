package android.assignment.api;


import android.assignment.BuildConfig;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class ApiHttpClient {

    public OkHttpClient getHTTPClient(final Map<String, String> headers) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (BuildConfig.API_LOGGING)
            httpClient.addInterceptor(logging);

        return httpClient.build();

    }
}