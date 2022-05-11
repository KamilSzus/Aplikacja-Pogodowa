package com.example.aplikacja_pogodowa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterFinder extends RecyclerView.Adapter<RecyclerViewAdapterFinder.ViewHolderFinder>{

    private final ArrayList<String> cityList;
    private final ClickListenerFinder clickListenerFinder;

    public RecyclerViewAdapterFinder(ArrayList<String> cityList, ClickListenerFinder clickListenerFinder) {
        this.cityList = cityList;
        this.clickListenerFinder = clickListenerFinder;
    }

    @NonNull
    @Override
    public ViewHolderFinder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == R.layout.city_row) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_row, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finder, parent, false);
        }

        return new ViewHolderFinder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFinder holder, int position) {
        if(position==0){
            holder.apply.setOnClickListener(clickListenerFinder::onClickApply);
            holder.addToFavorite.setImageResource(R.drawable.ulubione);
            holder.addToFavorite.setOnClickListener(clickListenerFinder::onClickAddToFavorite);
        }else{
            holder.cityName.setText(cityList.get(position));
            holder.alreadyAdded.setImageResource(R.drawable.ulubione);
            holder.alreadyAdded.setOnClickListener(clickListenerFinder::onClickAlreadyAdded);
            holder.deleteFromFavorite.setImageResource(R.drawable.trash);
            //holder.deleteFromFavorite.setOnClickListener(clickListenerFinder.onClickTrash(position));
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return (position == 0) ? R.layout.finder : R.layout.city_row;
    }

    @Override
    public int getItemCount() {
        return cityList.size()+1;
    }

    public static class ViewHolderFinder extends RecyclerView.ViewHolder {
        EditText findCity;
        ImageButton addToFavorite;
        Button apply;
        TextView cityName;
        ImageButton deleteFromFavorite;
        ImageButton alreadyAdded;

        public ViewHolderFinder(@NonNull View itemView) {
            super(itemView);

            findCity = itemView.findViewById(R.id.CityFinder);
            addToFavorite = itemView.findViewById(R.id.starIconFinder);
            apply = itemView.findViewById(R.id.apply);
            cityName = itemView.findViewById(R.id.City);
            deleteFromFavorite = itemView.findViewById(R.id.delete);
            alreadyAdded = itemView.findViewById(R.id.starIcon);

        }
    }
}
