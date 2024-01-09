package com.example.ruo;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class APIClient {

    public static Retrofit getClient() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logger.setLevel(HttpLoggingInterceptor.Level.BODY)).build();

        return new Retrofit.Builder().baseUrl("https://2nhj8ts5-3000.use.devtunnels.ms/").addConverterFactory(GsonConverterFactory.create()).client(client).build();
    }

}
