package com.example.aplikacja_pogodowa.Download;

import com.android.volley.VolleyError;
import com.example.aplikacja_pogodowa.Download.WeatherData;

public interface VolleyCallback {
    void onSuccessResponse(WeatherData result);
    void onErrorResponse(VolleyError error);
    void onSuccessResponseImage(WeatherData bitmap);
    void onErrorResponseImage(VolleyError error);
}
