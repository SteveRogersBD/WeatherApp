package com.example.weatherapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofitCity;
    private static Retrofit retrofitPosition;
    public static Retrofit getClientCity(){
        if(retrofitCity==null)
        {
            retrofitCity = new Retrofit.Builder().baseUrl("http://api.openweathermap.org/geo/1.0/").
                    addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitCity;
    }

    public static Retrofit getClientPosition(){
        if(retrofitPosition==null)
        {
            retrofitPosition = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").
                    addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitPosition;
    }
}
