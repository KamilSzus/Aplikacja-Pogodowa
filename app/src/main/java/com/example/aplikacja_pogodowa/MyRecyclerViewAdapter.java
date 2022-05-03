package com.example.aplikacja_pogodowa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final List<String> mData;

    public MyRecyclerViewAdapter(List<String> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_row, parent, false);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int height = view.getMeasuredHeight();
        return new ViewHolder(view, height);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.myTextViewHours.setText(mData.get(position));
        holder.myTextViewTemperature.setText(mData.get(position));
        holder.imageView.setImageResource(R.drawable.temp);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.hours_row;
    }

    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView myTextViewTemperature;
        TextView myTextViewHours;
        ImageView imageView;

        ViewHolder(View itemView, int height) {
            super(itemView);
            itemView.setMinimumHeight(height);
            myTextViewTemperature = itemView.findViewById(R.id.temp3);
            myTextViewHours = itemView.findViewById(R.id.hours);
            imageView = itemView.findViewById(R.id.WeatherIcon);
        }
    }

}