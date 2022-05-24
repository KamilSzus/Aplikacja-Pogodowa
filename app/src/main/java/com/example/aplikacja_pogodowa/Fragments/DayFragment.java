package com.example.aplikacja_pogodowa.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.example.aplikacja_pogodowa.Download.DownloadFile;
import com.example.aplikacja_pogodowa.Download.DownloadImage;
import com.example.aplikacja_pogodowa.Download.VolleyCallback;
import com.example.aplikacja_pogodowa.Download.WeatherData;
import com.example.aplikacja_pogodowa.MainActivity;
import com.example.aplikacja_pogodowa.R;
import com.example.aplikacja_pogodowa.MVVM.ViewModel;

import java.text.DecimalFormat;

public class DayFragment extends Fragment implements VolleyCallback {

    private WeatherData weatherData;
    private View view;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        Button refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(v -> createFile());

        final Observer<WeatherData> weatherDataObserver = weatherData1 -> {
            refreshData(weatherData1);
            refreshImage(weatherData1);
        };

        ViewModel model = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        model.getWeatherData().observe(getViewLifecycleOwner(),weatherDataObserver);
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