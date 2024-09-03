package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.weatherapplication.ApiInterfaces.ApiInterfaceWeather;
import com.example.weatherapplication.Responses.WeatherResponse;
import com.example.weatherapplication.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        double longitude = intent.getDoubleExtra("longitude",0.0);
        double latitude = intent.getDoubleExtra("latitude",0.0);
        String city = intent.getStringExtra("city").trim().toUpperCase();
        String country = intent.getStringExtra("country");
        String position = city+", "+country;
        binding.tvLocation.setText(position);

        binding.tvLongitude.setText("Longitude: "+longitude);
        binding.tvLatitude.setText("Latitude: "+latitude);

        ApiInterfaceWeather apiInterfaceWeather = RetrofitInstance.getClientPosition().create(ApiInterfaceWeather.class);
        apiInterfaceWeather.getWeather(latitude,longitude,getString(R.string.apiKey)).
                enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if(response.isSuccessful() && response.body()!=null)
                        {
                            try{
                                inflate(response.body());
                            }catch (Exception e)
                            {
                                Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void inflate(WeatherResponse weatherResponse){
        binding.tvTemp.setText("Temperature: "+weatherResponse.main.temp);
        binding.tvFeelsLike.setText("Feels like: "+weatherResponse.main.feels_like+"");
        binding.tvMaxTemp.setText("Max Temp: "+weatherResponse.main.temp_max+"");
        binding.tvMinTemp.setText("Min Temp: "+weatherResponse.main.temp_min+"");
        binding.tvPressure.setText("Pressure: "+weatherResponse.main.pressure+"");
        binding.tvHumidity.setText("Humidity :"+weatherResponse.main.humidity+"");

        binding.tvSpeed.setText("Speed: "+weatherResponse.wind.speed+"");
        binding.tvDegree.setText("Humidity: "+weatherResponse.wind.deg+"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}