package android.assignment.di.modules;

import android.assignment.api.ApiEnvelopeService;
import android.assignment.api.ApiHttpClient;
import android.assignment.api.ApiService;
import android.assignment.api.converter.ApiConverterFactory;
import android.assignment.api.converter.ApiEnvelopeConverterFactory;
import android.assignment.utils.ApiUtils;


import java.util.HashMap;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiClientModule {
    @Provides
    @Singleton
    public ApiService getApiService() {

        Retrofit apiClient = new Retrofit.Builder()
                .baseUrl(ApiUtils.getApiBaseUrl())
                .addConverterFactory(new ApiConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new ApiHttpClient().getHTTPClient(new HashMap<String, String>()))
                .build();

        return apiClient.create(ApiService.class);

    }

    @Provides
    @Singleton
    public ApiEnvelopeService getEnvelopeApiService() {

        Retrofit apiClient = new Retrofit.Builder()
                .baseUrl(ApiUtils.getApiBaseUrl())
                .addConverterFactory(new ApiEnvelopeConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new ApiHttpClient().getHTTPClient(new HashMap<String, String>()))
                .build();

        return apiClient.create(ApiEnvelopeService.class);

    }
}
