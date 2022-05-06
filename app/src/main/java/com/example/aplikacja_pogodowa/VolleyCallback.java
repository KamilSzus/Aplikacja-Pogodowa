package com.example.aplikacja_pogodowa;

import com.android.volley.VolleyError;

public interface VolleyCallback {
    void onSuccessResponse(WeatherData result);
    void onErrorResponse(VolleyError error);
}
