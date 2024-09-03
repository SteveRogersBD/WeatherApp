package com.example.weatherapplication.ApiInterfaces;

import com.example.weatherapplication.Responses.CityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceCity {

    @GET("direct")
    Call<List<CityResponse>> getLocation(
            @Query("q") String city,
            @Query("appid") String apiKey

    );
}
