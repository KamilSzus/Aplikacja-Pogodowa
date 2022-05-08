package com.example.aplikacja_pogodowa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DaysFragment extends Fragment  {

    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_days, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bundle = this.getArguments();
        if (bundle != null) {
            WeatherData weatherData = (WeatherData) bundle.getSerializable("WeatherData");
            recyclerView.setAdapter(new MyRecyclerViewAdapter(weatherData.getTemperatureList(), weatherData.getDayList(), weatherData.getTimeZone() , v -> {
                Fragment fragment = new DayFragment();
                fragment.setArguments(bundle);
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainLayout, fragment)
                        .setReorderingAllowed(true)
                        .commit();
            }));
        }

        return view;
    }
}