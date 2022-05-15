package com.example.aplikacja_pogodowa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class CityFragment extends Fragment implements ClickListenerFinder {

    Bundle bundle;
    RecyclerViewAdapterFinder adapter;
    ArrayList<String> cityList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewsCity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cityList.add("Łódź");
        adapter = new RecyclerViewAdapterFinder(cityList,this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClickTrash(int index) {
        cityList.remove(index);
        adapter.notifyItemRemoved(index+1);
    }

    @Override
    public void onClickAlreadyAdded(View v) {

    }

    @Override
    public void onClickApply(int position, String city) {
        ((MainActivity) requireActivity()).setTextViewCity(city);
        ((MainActivity) requireActivity()).readFile();

    }

    @Override
    public void onClickAddToFavorite(int position, String city) {
        cityList.add(city);
        adapter.notifyItemInserted(position+1);
    }
}