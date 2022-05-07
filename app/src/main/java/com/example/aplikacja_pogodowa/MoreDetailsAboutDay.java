package com.example.aplikacja_pogodowa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MoreDetailsAboutDay extends Fragment {

    Button back;
    Bundle bundle;

    public MoreDetailsAboutDay() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

            TextView windStrength = view.findViewById(R.id.WindStrength);
            windStrength.setText(weatherData.getWindStrength());

            TextView humidity = view.findViewById(R.id.Humidity);
            humidity.setText(weatherData.getHumidity());

            TextView sunrise = view.findViewById(R.id.Sunrise);
            sunrise.setText(weatherData.getSunrise());

            TextView sunset = view.findViewById(R.id.Sunset);
            sunset.setText(weatherData.getSunset());
        }
        return view;
    }
}