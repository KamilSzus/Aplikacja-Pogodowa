package com.example.aplikacja_pogodowa;

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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.net.MalformedURLException;
import java.net.URL;

public class DayFragment extends Fragment {

    private GestureDetector mDetector;
    NetworkImageView weatherIcon;
    ImageLoader imageLoader;
    Bundle bundle;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        weatherIcon = view.findViewById(R.id.WeatherIcon);
        Button buttonMoreDetails = view.findViewById(R.id.buttonMoreDetails);

        buttonMoreDetails.setOnClickListener(v -> {
            Fragment fragment = new MoreDetailsAboutDay();
            fragment.setArguments(bundle);
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainLayout, fragment)
                    .setReorderingAllowed(true)
                    .commit();
        });

        gesture(view);

        bundle = this.getArguments();
        if (bundle != null) {
            WeatherData weatherData = (WeatherData) bundle.getSerializable("WeatherData");

            TextView temperatureData = view.findViewById(R.id.TemperatureData);
            temperatureData.setText(weatherData.getTemperature());

            TextView latData = view.findViewById(R.id.LatitudeData);
            latData.setText(weatherData.getLatitude());

            TextView lonData = view.findViewById(R.id.LongitudeData);
            lonData.setText(weatherData.getLongitude());
        }

        URL u = null;
        try {
            u = new URL("http://openweathermap.org/img/wn/10d@2x.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        imageLoader = DownloadImage.getInstance(this.getContext())
                .getImageLoader();
        imageLoader.get(u.toString(), ImageLoader.getImageListener(weatherIcon, R.drawable.finding, R.drawable.finding));
        weatherIcon.setImageUrl(u.toString(), imageLoader);

        return view;
    }

    private void gesture(View view) {
        mDetector = new GestureDetector(getActivity(), new SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent event) {

                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    final int SWIPE_THRESHOLD = 100;
                    final int SWIPE_VELOCITY_THRESHOLD = 100;
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            System.out.println("gora dol");
                            requireActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.mainLayout, new FiveDaysFragment())
                                    .commit();
                            result = true;
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        });
        view.setOnTouchListener(touchListener);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return mDetector.onTouchEvent(event);

        }
    };
}