package com.example.aplikacja_pogodowa.Download;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class DownloadImage {

    private String url = "https://openweathermap.org/img/wn/";
    private final Context context;
    private final VolleyCallback callback;
    private WeatherData weatherData;


    public DownloadImage(Context context, VolleyCallback callback, WeatherData data) {
        this.context = context;
        this.callback = callback;
        weatherData = data;
    }

    public void start(){
        getResponse(weatherData.getImageIcon());
    }

    public void getResponse(List<String> bitmapList) {
        bitmapList.forEach(icon -> {
            String urlForImage = url + icon + "@2x.png";
            System.out.println(urlForImage);
            RequestQueue mRequestQueue = Volley.newRequestQueue(context);
            ImageRequest request = new ImageRequest(urlForImage, response -> {
                weatherData.getImageList().add(response);
                System.out.println(urlForImage);
                if(weatherData.getImageList().size()==weatherData.getImageIcon().size()){
                    callback.onSuccessResponseImage(weatherData);
                }
            }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                    callback::onErrorResponseImage);
            mRequestQueue.add(request);

        });
    }
}
