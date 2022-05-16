package com.example.aplikacja_pogodowa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CityFragment extends Fragment implements ClickListenerFinder {

    RecyclerViewAdapterFinder adapter;
    ArrayList<String> cityList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewsCity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readFile();
        adapter = new RecyclerViewAdapterFinder(cityList,this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void readFile() {
        try {
            FileInputStream fileInputStream = requireActivity().getApplicationContext().openFileInput("City.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.lines().forEach(cityList::add);
        } catch (IOException ignored) {
            new File(requireActivity().getApplicationContext().getFilesDir(), "City.txt");
        }
    }

    private void loadNewFragment(String city){
        updateCityFile();
        ((MainActivity) requireActivity()).setTextViewCity(city);
        ((MainActivity) requireActivity()).createFile();
    }

    private void updateCityFile() {
        try {
            FileOutputStream outputStream = requireActivity().getApplicationContext().openFileOutput("City.txt", Context.MODE_PRIVATE);
            cityList.forEach(s -> {
                try {
                    s=s+"\n";
                    outputStream.write(s.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClickTrash(int position) {
        cityList.remove(position);
        adapter.notifyItemRemoved(position+1);
    }

    @Override
    public void onClickAlreadyAdded(int position) {
        loadNewFragment(cityList.get(position));
    }

    @Override
    public void onClickApply(int position, String city) {
        loadNewFragment(city);
    }

    @Override
    public void onClickAddToFavorite(int position, String city) {
        cityList.add(0,city);
        adapter.notifyItemInserted(position+1);//????
    }
}