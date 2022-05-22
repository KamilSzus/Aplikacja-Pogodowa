package com.example.aplikacja_pogodowa.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.example.aplikacja_pogodowa.Download.DownloadFile;
import com.example.aplikacja_pogodowa.Download.DownloadImage;
import com.example.aplikacja_pogodowa.Download.VolleyCallback;
import com.example.aplikacja_pogodowa.Download.WeatherData;
import com.example.aplikacja_pogodowa.MainActivity;
import com.example.aplikacja_pogodowa.MoreDays.DaysFragment;
import com.example.aplikacja_pogodowa.R;

import java.text.DecimalFormat;

public class DayFragment extends Fragment implements VolleyCallback {

    private View view;
    private WeatherData weatherData;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_day, container, false);
        Button refresh = view.findViewById(R.id.refresh);
        Button units = view.findViewById(R.id.changeUnits);
        units.setOnClickListener(v -> changeUnits());
        refresh.setOnClickListener(v -> createFile());

        if (((MainActivity) requireActivity()).getWeatherData() != null) {
            weatherData = ((MainActivity) requireActivity()).getWeatherData();
            refreshData(weatherData);
            refreshImage(weatherData);
        }

        return view;
    }

    private void changeUnits() {
        if(weatherData!=null) {
            ChangeUnits units = new ChangeUnits(weatherData);
            units.run();
            refreshData(weatherData);
        }
    }

    public void createFile() {
        DownloadFile downloadFile = new DownloadFile(requireActivity().getApplicationContext(), this);
        downloadFile.downloadNewData(((MainActivity) requireActivity()).getTextViewCity());
    }

    @Override
    public void onSuccessResponse(WeatherData result) {
        refreshData(result);
        DownloadImage downloadImage = new DownloadImage(requireActivity().getApplicationContext(), this, result);
        downloadImage.start();
    }

    private void refreshData(WeatherData result) {
        DecimalFormat df = new DecimalFormat("#.##");
        weatherData = result;
        TextView temperatureData = view.findViewById(R.id.TemperatureData);
        temperatureData.setText(df.format(result.getTemperature()));

        TextView weatherText = view.findViewById(R.id.Weather);
        weatherText.setText(result.getWeather());

        TextView latData = view.findViewById(R.id.LatitudeData);
        latData.setText(df.format(result.getLatitude()));

        TextView lonData = view.findViewById(R.id.LongitudeData);
        lonData.setText(df.format(result.getLongitude()));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onSuccessResponseImage(WeatherData bitmap) {
        refreshImage(bitmap);
    }

    private void refreshImage(WeatherData bitmap) {
        NetworkImageView weatherIcon = view.findViewById(R.id.WeatherIcon);
        weatherIcon.setDefaultImageBitmap(bitmap.getImageList().get(0));
    }

    @Override
    public void onErrorResponseImage(VolleyError error) {
        error.printStackTrace();
    }
}