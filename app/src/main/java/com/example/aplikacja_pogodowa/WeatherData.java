package com.example.aplikacja_pogodowa;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherData implements Serializable {
    private String cityName;
    private Double temperature;
    private Double latitude;
    private Double longitude;
    private String windDirection;
    private String windStrength;
    private Integer sunrise;
    private Integer sunset;
    private Integer humidity;
    private Integer visibility;
    private String timeZone;
    private String units;
    private String Weather;

    private ArrayList<Double> temperatureList = new ArrayList<>();
    private ArrayList<Integer> dayList = new ArrayList<>();
    private ArrayList<Bitmap> imageList = new ArrayList<>();
    private ArrayList<String> imageIcon = new ArrayList<>();

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String weather) {
        Weather = weather;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public ArrayList<String> getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ArrayList<String> imageIcon) {
        this.imageIcon = imageIcon;
    }

    public ArrayList<Bitmap> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<Bitmap> imageList) {
        this.imageList = imageList;
    }

    public ArrayList<Double> getTemperatureList() {
        return temperatureList;
    }

    public void setTemperatureList(ArrayList<Double> temperatureList) {
        this.temperatureList = temperatureList;
    }

    public ArrayList<Integer> getDayList() {
        return dayList;
    }

    public void setDayList(ArrayList<Integer> dayList) {
        this.dayList = dayList;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindStrength() {
        return windStrength;
    }

    public void setWindStrength(String windStrength) {
        this.windStrength = windStrength;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }
}
