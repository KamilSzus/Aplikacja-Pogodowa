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

    private GestureDetector mDetector;
    private View view;
    private WeatherData weatherData;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_day, container, false);
        Button buttonMoreDetails = view.findViewById(R.id.buttonMoreDetails);
        Button refresh = view.findViewById(R.id.refresh);
        Button units = view.findViewById(R.id.changeUnits);
        units.setOnClickListener(v -> changeUnits());
        refresh.setOnClickListener(v ->createFile());

        buttonMoreDetails.setOnClickListener(v -> {
            Fragment fragment = new MoreDetailsAboutDay();
            Bundle bundle = new Bundle();
            bundle.putSerializable("WeatherData" ,weatherData);
            fragment.setArguments(bundle);
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainLayout, fragment)
                    .setReorderingAllowed(true)
                    .commit();
        });
        gesture(view);
        if (this.getArguments() != null) {
            weatherData = (WeatherData) this.getArguments().getSerializable("WeatherData");
            refreshData(weatherData);
            refreshImage(weatherData);
        }

        return view;
    }

    private void changeUnits(){
        ChangeUnits units= new ChangeUnits(weatherData);
        units.run();
        refreshData(weatherData);
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
                            Fragment fragment = new DaysFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("WeatherData" ,weatherData);
                            fragment.setArguments(bundle);
                            getParentFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.mainLayout, fragment)
                                    .setReorderingAllowed(true)
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
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return mDetector.onTouchEvent(event);
        }
    };

    public void createFile() {
        DownloadFile downloadFile = new DownloadFile(requireActivity().getApplicationContext(), this);
        downloadFile.downloadNewData(((MainActivity) requireActivity()).getTextViewCity());
    }

    @Override
    public void onSuccessResponse(WeatherData result) {
        refreshData(result);
        DownloadImage downloadImage = new DownloadImage(requireActivity().getApplicationContext(), this,result);
        downloadImage.start();
    }

    private void refreshData(WeatherData result) {
        DecimalFormat df = new DecimalFormat("#.##");
        TextView temperatureData = view.findViewById(R.id.TemperatureData);
        temperatureData.setText(df.format(result.getTemperature()));

        TextView weatherText = view.findViewById(R.id.Weather);
        weatherText.setText(weatherData.getWeather());

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