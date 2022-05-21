package com.example.aplikacja_pogodowa.Fragments;

import com.example.aplikacja_pogodowa.Download.WeatherData;

import java.util.ArrayList;

public class ChangeUnits implements Runnable{
    private final WeatherData weatherData;

    public ChangeUnits(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public void run() {
        Double converter = 273.15;
        if(weatherData.getUnits().equals("Unnatural")) {
            weatherData.setTemperature(weatherData.getTemperature() - converter);
            ArrayList<Double> temp = weatherData.getTemperatureList();
            for (int i = 0; i < temp.size(); i++) {
                temp.set(i, temp.get(i) - converter);
            }
            weatherData.setUnits("Natural");
        }else{
            weatherData.setTemperature(weatherData.getTemperature() + converter);
            ArrayList<Double> temp = weatherData.getTemperatureList();
            for (int i = 0; i < temp.size(); i++) {
                temp.set(i, temp.get(i) + converter);
            }
            weatherData.setUnits("Unnatural");
        }
    }

}
