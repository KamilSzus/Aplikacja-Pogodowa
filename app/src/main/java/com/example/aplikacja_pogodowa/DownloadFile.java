package com.example.aplikacja_pogodowa;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.GeoPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DownloadFile extends AppCompatActivity implements Runnable  {
    private String url;
    private Context context;

    //obecna pogoda
    //na 5 dni
    //String url = api.openweathermap.org/data/2.5/forecast?q={city name}&appid=ef6c28ac0f2520bf3fbcca2039cb8799
    // http://openweathermap.org/img/wn/10d@2x.png
    // https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=hourly,daily&appid={API key}
    String appId = "ef6c28ac0f2520bf3fbcca2039cb8799";
    private final String FILE_NAME = "Weather.Json";

    public File jsonFile;

    public DownloadFile(Context context) {
        this.context = context;
    }

      @Override
 public void run() {
          RequestQueue mRequestQueue = Volley.newRequestQueue(context);

          StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
              try {
                  JSONObject jsonResponse = new JSONObject(response);
                  System.out.println(jsonResponse);
                  SaveFile(jsonResponse);

                  Bundle jsonBundle = new Bundle();
                  jsonBundle.putString("JsonWeather", jsonResponse.toString());
                  getSupportFragmentManager().setFragmentResult("JsonWeather", jsonBundle);

              } catch (JSONException | IOException e) {
                  e.printStackTrace();
              }
          }, System.out::println);

          mRequestQueue.add(stringRequest);

      }

      private void SaveFile(JSONObject jsonResponse) throws IOException {

          FileWriter fileWriter = new FileWriter(jsonFile);
          BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
          bufferedWriter.write(jsonResponse.toString());
          System.out.println(jsonResponse);
          bufferedWriter.close();

      }
    public GeoPoint getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint(location.getLatitude() * 1E6,
                    location.getLongitude() * 1E6);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }
    public void start(String city) {
        url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=ef6c28ac0f2520bf3fbcca2039cb8799";
        jsonFile = new File(context.getFilesDir(),FILE_NAME);
        Thread thread = new Thread(this);
        thread.start();
    }
}
