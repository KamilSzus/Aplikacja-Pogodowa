package com.example.aplikacja_pogodowa.Finder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikacja_pogodowa.R;

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
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int height = view.getMeasuredHeight();
        return new ViewHolderFinder(view,clickListenerFinder,height);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFinder holder, int position) {
        if(position==0){
            holder.addToFavorite.setImageResource(R.drawable.ulubione);
        }else{
            holder.cityName.setText(cityList.get(position-1));
            holder.alreadyAdded.setImageResource(R.drawable.ulubione);
            holder.deleteFromFavorite.setImageResource(R.drawable.trash);
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

        public ViewHolderFinder(@NonNull View itemView, ClickListenerFinder clickListenerFinder, int height) {
            super(itemView);

            itemView.setMinimumHeight(height);
            findCity = itemView.findViewById(R.id.CityFinder);
            addToFavorite = itemView.findViewById(R.id.addToFavorite);
            apply = itemView.findViewById(R.id.apply);
            cityName = itemView.findViewById(R.id.City);
            deleteFromFavorite = itemView.findViewById(R.id.delete);
            alreadyAdded = itemView.findViewById(R.id.starIcon);

            itemView.setOnClickListener(v -> clickListenerFinder.onClickAlreadyAdded(getBindingAdapterPosition()-1));

            if(deleteFromFavorite!=null) {
                deleteFromFavorite.setOnClickListener(v -> clickListenerFinder.onClickTrash(getBindingAdapterPosition()-1));
            }
            if(alreadyAdded!=null){
                alreadyAdded.setOnClickListener(v -> System.out.println("AAAAAA"));
            }
            if(addToFavorite!=null){
                addToFavorite.setOnClickListener(v -> clickListenerFinder.onClickAddToFavorite(getBindingAdapterPosition(),findCity.getText().toString()));
            }
            if(apply!=null){
                apply.setOnClickListener(v -> clickListenerFinder.onClickApply(0,findCity.getText().toString()));
            }

        }
    }
}
