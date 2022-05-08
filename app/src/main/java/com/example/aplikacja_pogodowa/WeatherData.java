package com.example.aplikacja_pogodowa;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherData implements Serializable {
    private String temperature;
    private String latitude;
    private String longitude;
    private String windDirection;
    private String windStrength;
    private String sunrise;
    private String sunset;
    private String humidity;
    private String visibility;
    private String timeZone;
    private ArrayList<String> temperatureList = new ArrayList<>();
    private ArrayList<String> dayList = new ArrayList<>();

    public ArrayList<String> getTemperatureList() {
        return temperatureList;
    }

    public void setTemperatureList(ArrayList<String> temperatureList) {
        this.temperatureList = temperatureList;
    }

    public ArrayList<String> getDayList() {
        return dayList;
    }

    public void setDayList(ArrayList<String> dayList) {
        this.dayList = dayList;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
