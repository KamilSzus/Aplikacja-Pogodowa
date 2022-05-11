package com.example.aplikacja_pogodowa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CityFragment extends Fragment implements ClickListenerFinder {

    Bundle bundle;

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
        bundle = this.getArguments();
        recyclerView.setAdapter(new RecyclerViewAdapterFinder(cityList,this));

        return view;
    }

    @Override
    public void onClickTrash(int index) {
        cityList.remove(index);
    }

    @Override
    public void onClickAlreadyAdded(View v) {
        System.out.println("ad");
    }

    @Override
    public void onClickApply(View v) {
        System.out.println("ad");
    }

    @Override
    public void onClickAddToFavorite(View v) {
       cityList.add("aaaa");
    }
}