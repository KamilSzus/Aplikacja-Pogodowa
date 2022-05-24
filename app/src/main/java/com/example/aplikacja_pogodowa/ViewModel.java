package com.example.aplikacja_pogodowa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aplikacja_pogodowa.Download.WeatherData;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<WeatherData> weatherData = new MutableLiveData<>();

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData.setValue(weatherData);
    }

    public LiveData<WeatherData> getWeatherData(){
        return this.weatherData;
    }
}
