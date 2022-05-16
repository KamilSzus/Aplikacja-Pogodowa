package com.example.aplikacja_pogodowa;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.GeoPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DownloadFile extends AppCompatActivity {
    private String url;
    private final Context context;
    private final VolleyCallback callback;
    private final WeatherData weatherData = new WeatherData();
    private final String appId = "ef6c28ac0f2520bf3fbcca2039cb8799";
    private final String FILE_NAME = "Weather.Json";


    public DownloadFile(Context context, VolleyCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    private void SaveFile(JSONObject jsonResponse) throws IOException, JSONException {
        jsonResponse.put("city",weatherData.getCityName());
        File jsonFile = new File(context.getFilesDir(), FILE_NAME);
        FileWriter fileWriter = new FileWriter(jsonFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonResponse.toString());
        System.out.println(jsonResponse);
        bufferedWriter.close();
    }

    private GeoPoint getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        GeoPoint p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address.size() == 0) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new GeoPoint(location.getLatitude(),
                    location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }

    private void getResponse() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                SaveFile(jsonResponse);

                readData(jsonResponse);
                callback.onSuccessResponse(weatherData);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }, callback::onErrorResponse);

        mRequestQueue.add(request);
    }

    private void readData(JSONObject jsonResponse) throws JSONException {
        readDataFromJson(jsonResponse);
        readDataFromJsonForDays(jsonResponse);
        readIconFromJson(jsonResponse);
    }

    private void readDataFromJson(JSONObject jsonResponse) throws JSONException {
        weatherData.setLongitude(Double.toString(jsonResponse.getDouble("lon")));
        weatherData.setLatitude(Double.toString(jsonResponse.getDouble("lat")));
        weatherData.setTimeZone(jsonResponse.getString("timezone"));
        JSONObject object = jsonResponse.getJSONObject("current");
        weatherData.setHumidity(Integer.toString(object.getInt("humidity")));
        weatherData.setVisibility(Integer.toString(object.getInt("visibility")));
        weatherData.setSunrise(Integer.toString(object.getInt("sunrise")));
        weatherData.setSunset(Integer.toString(object.getInt("sunset")));
        weatherData.setWindStrength(Double.toString(object.getDouble("wind_speed")));
        weatherData.setTemperature(Double.toString(object.getDouble("temp")));
    }

    private void readDataFromJsonForDays(JSONObject jsonResponse) throws JSONException {
        JSONArray jsonArray = jsonResponse.getJSONArray("daily");
        for (int i = 1; i < jsonArray.length(); i++) {
            weatherData.getDayList().add(Integer.toString(jsonArray.getJSONObject(i)
                    .getInt("dt")));
            weatherData.getTemperatureList().add(Double.toString(jsonArray.getJSONObject(i)
                    .getJSONObject("temp")
                    .getDouble("day")));
        }
    }

    private void readIconFromJson(JSONObject jsonResponse) throws JSONException {
        JSONArray jsonArray = jsonResponse.getJSONArray("daily");
        for (int i = 0; i < jsonArray.length(); i++) {
            weatherData.getImageIcon().add(jsonArray.getJSONObject(i)
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("icon"));
        }
    }

    public void downloadNewData(String city) {
        GeoPoint coder = getLocationFromAddress(city);
        if (coder != null) {
            weatherData.setCityName(city);
            url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + coder.getLatitude() + "&lon=" + coder.getLongitude() + "&lang=pl&exclude=minutely&appid=" + appId;
            getResponse();
        } else {
            Toast.makeText(context, "Miasto nie istnieje", Toast.LENGTH_SHORT).show();
        }
    }

    public WeatherData setData(String json) {
        try {
            JSONObject object = new JSONObject(json);
            readData(object);
            weatherData.setCityName(object.getString("city"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherData;
    }
}
