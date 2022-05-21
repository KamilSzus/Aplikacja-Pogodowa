package com.example.aplikacja_pogodowa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MoreDetailsAboutDay extends Fragment {

    Button back;
    Bundle bundle;

    public MoreDetailsAboutDay() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_details_about_day, container, false);
        back = view.findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Fragment fragment = new DayFragment();
            fragment.setArguments(bundle);
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainLayout,fragment)
                    .setReorderingAllowed(true)
                    .commit();
        });

        bundle= this.getArguments();
        if (bundle != null) {
            WeatherData weatherData = (WeatherData) bundle.getSerializable("WeatherData");

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
        return view;
    }

    private String convertTime(String time,String timeZone) {
        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");

        final long unixTime = Long.parseLong(time);
        return Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of(timeZone))
                .format(formatter);
    }
}