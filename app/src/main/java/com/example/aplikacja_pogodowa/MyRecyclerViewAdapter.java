package com.example.aplikacja_pogodowa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final List<String> mData;
    private CustomItemClickListener mClickListener;

    public MyRecyclerViewAdapter(List<String> data, CustomItemClickListener listener) {
        this.mData = data;
        this.mClickListener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == R.layout.hours_row) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_row, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button, parent, false);
        }

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int height = view.getMeasuredHeight();
        return new ViewHolder(view, height);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == mData.size()) {
            holder.button.setOnClickListener(v -> {
                mClickListener.onItemClick(v);
            });
        } else {
            holder.myTextViewHours.setText(mData.get(position));
            holder.myTextViewTemperature.setText(mData.get(position));
            holder.imageView.setImageResource(R.drawable.temp);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    @Override
    public int getItemViewType(final int position) {
        return (position == mData.size()) ? R.layout.button : R.layout.hours_row;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTextViewTemperature;
        TextView myTextViewHours;
        ImageView imageView;
        Button button;

        ViewHolder(View itemView, int height) {
            super(itemView);
            itemView.setMinimumHeight(height);
            myTextViewTemperature = itemView.findViewById(R.id.temp3);
            myTextViewHours = itemView.findViewById(R.id.hours);
            imageView = itemView.findViewById(R.id.WeatherIcon);
            button = itemView.findViewById(R.id.back);
        }
    }

}