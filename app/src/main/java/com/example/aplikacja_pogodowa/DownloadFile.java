package com.example.aplikacja_pogodowa;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DownloadFile implements Runnable {
    private String url;
    final private Context context;


    //obecna pogoda
    //na 5 dni
    //String url = api.openweathermap.org/data/2.5/forecast?q={city name}&appid=ef6c28ac0f2520bf3fbcca2039cb8799"
    String appId = "ef6c28ac0f2520bf3fbcca2039cb8799";

    public DownloadFile(Context context) {
        this.context = context;
    }
      @Override
 public void run() {
          System.out.println("BBBBBBB");
          System.out.println(url);
          RequestQueue mRequestQueue = Volley.newRequestQueue(context);
          StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
              System.out.println("CCCCCCCC");

              String output = "";
              try {
                  JSONObject jsonResponse = new JSONObject(response);
                  System.out.println(jsonResponse);

              } catch (JSONException e) {
                  System.out.println("DDDDDDD");

                  e.printStackTrace();
              }
          }, System.out::println);

          mRequestQueue.add(stringRequest);
      }

    public void start(String city) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa");
        url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=ef6c28ac0f2520bf3fbcca2039cb8799";
        Thread thread = new Thread(this);
        thread.start();
    }
}
