package com.example.aplikacja_pogodowa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CityFragment extends Fragment implements ClickListenerFinder {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onClickTrash(View v) {

    }

    @Override
    public void onClickAlreadyAdded(View v) {

    }

    @Override
    public void onClickApply(View v) {

    }

    @Override
    public void onClickAddToFavorite(View v) {

    }
}