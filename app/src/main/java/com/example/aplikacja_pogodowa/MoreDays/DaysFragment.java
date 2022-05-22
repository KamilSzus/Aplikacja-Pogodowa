package com.example.aplikacja_pogodowa.MoreDays;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikacja_pogodowa.Download.WeatherData;
import com.example.aplikacja_pogodowa.Fragments.DayFragment;
import com.example.aplikacja_pogodowa.MoreDays.MyRecyclerViewAdapter;
import com.example.aplikacja_pogodowa.R;

public class DaysFragment extends Fragment{

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
            recyclerView.setAdapter(new MyRecyclerViewAdapter(weatherData, v -> {
            }));
        }

        return view;
    }
}