package com.codecamp.codecamp06;

import android.app.Application;

import com.codecamp.codecamp06.rest.ApiDescription;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Application class.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/10/2016.
 */
public class App extends Application {
    public static final String TAG = App.class.getName();

    private ApiDescription apiDescription;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Stetho.initializeWithDefaults(this);

        initRetrofit();
    }

    private void initRetrofit() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient.Builder()
                        .addNetworkInterceptor(new StethoInterceptor())
                        .build())
                .build();

        apiDescription = retrofit.create(ApiDescription.class);
    }

    public ApiDescription getApiDescription() {
        return apiDescription;
    }
}
