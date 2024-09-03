package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weatherapplication.ApiInterfaces.ApiInterfaceCity;
import com.example.weatherapplication.Responses.CityResponse;
import com.example.weatherapplication.databinding.ActivityFirstBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirstActivity extends AppCompatActivity {

    ActivityFirstBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityS = binding.cityName.getText().toString().toLowerCase();
                if(cityS.equals("")) binding.cityName.setError("Enter a valid city Name!");
                ApiInterfaceCity apiInterfaceCity = RetrofitInstance.getClientCity().
                        create(ApiInterfaceCity.class);
                apiInterfaceCity.getLocation(cityS,getString(R.string.apiKey))
                        .enqueue(new Callback<List<CityResponse>>() {
                            @Override
                            public void onResponse(Call<List<CityResponse>> call, Response<List<CityResponse>> response) {
                                if(response.isSuccessful() && response.body()!=null)
                                {
                                    try{
                                        double latitude = response.body().get(0).lat;
                                        double longitude = response.body().get(0).lon;
                                        String country = response.body().get(0).country;
                                        Intent intent = new Intent(FirstActivity.this,MainActivity.class);
                                        intent.putExtra("longitude",longitude);
                                        intent.putExtra("latitude",latitude);
                                        intent.putExtra("city",cityS);
                                        intent.putExtra("country",country);
                                        startActivity(intent);
                                    }catch (Exception e){
                                        Toast.makeText(FirstActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(FirstActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<CityResponse>> call, Throwable t) {
                                Toast.makeText(FirstActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }
}