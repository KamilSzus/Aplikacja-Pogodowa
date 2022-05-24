package com.example.aplikacja_pogodowa.Finder;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikacja_pogodowa.MainActivity;
import com.example.aplikacja_pogodowa.R;
import com.google.firebase.firestore.GeoPoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CityFragment extends Fragment implements ClickListenerFinder {

    private RecyclerViewAdapterFinder adapter;
    private final ArrayList<String> cityList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewsCity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readFile();
        adapter = new RecyclerViewAdapterFinder(cityList,this);
        recyclerView.setAdapter(adapter);
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

    private GeoPoint getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(requireActivity().getApplicationContext());
        List<Address> address;
        GeoPoint p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address.size() == 0) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new GeoPoint(location.getLatitude(),
                    location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }

    private void loadNewFragment(String city){
        if(getLocationFromAddress(city)!=null) {
            updateCityFile();
            ((MainActivity) requireActivity()).setTextViewCity(city);
            ((MainActivity) requireActivity()).createFile();
        }else {
            Toast.makeText(requireActivity().getApplicationContext(), "Miasto nie istnieje", Toast.LENGTH_SHORT).show();
        }
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