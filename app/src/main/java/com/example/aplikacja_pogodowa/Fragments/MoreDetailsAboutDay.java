package com.example.aplikacja_pogodowa.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aplikacja_pogodowa.Download.WeatherData;
import com.example.aplikacja_pogodowa.R;
import com.example.aplikacja_pogodowa.MVVM.ViewModel;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MoreDetailsAboutDay extends Fragment {

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_details_about_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view= view;

        final Observer<WeatherData> weatherDataObserver = this::refreshData;

        ViewModel model = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        model.getWeatherData().observe(getViewLifecycleOwner(),weatherDataObserver);

    }

    @SuppressLint("SetTextI18n")
    private void refreshData(WeatherData weatherData) {
        TextView timeZone = view.findViewById(R.id.TimeZoneData);
        timeZone.setText(weatherData.getTimeZone());

        TextView windStrength = view.findViewById(R.id.WindStrength);
        windStrength.setText(weatherData.getWindStrength());

        TextView humidity = view.findViewById(R.id.Humidity);
        humidity.setText(weatherData.getHumidity().toString());

        TextView visibility = view.findViewById(R.id.Visibility);
        visibility.setText(weatherData.getVisibility().toString());

        TextView sunrise = view.findViewById(R.id.Sunrise);
        sunrise.setText(convertTime(weatherData.getSunrise().toString(), weatherData.getTimeZone()));

        TextView sunset = view.findViewById(R.id.Sunset);
        sunset.setText(convertTime(weatherData.getSunset().toString(), weatherData.getTimeZone()));
    }

    private String convertTime(String time, String timeZone) {
        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");

        final long unixTime = Long.parseLong(time);
        return Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of(timeZone))
                .format(formatter);
    }
}