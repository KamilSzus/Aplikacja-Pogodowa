package com.example.aplikacja_pogodowa.MoreDays;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikacja_pogodowa.Download.WeatherData;
import com.example.aplikacja_pogodowa.R;
import com.example.aplikacja_pogodowa.MVVM.ViewModel;

public class DaysFragment extends Fragment{

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_days, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view=view;
        final Observer<WeatherData> weatherDataObserver = this::startRecyclerView;
        ViewModel model = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        model.getWeatherData().observe(getViewLifecycleOwner(),weatherDataObserver);
    }

    private void startRecyclerView(WeatherData weatherData) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(weatherData));
    }
}