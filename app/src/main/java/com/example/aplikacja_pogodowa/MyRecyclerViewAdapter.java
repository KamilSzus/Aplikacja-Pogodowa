package com.example.aplikacja_pogodowa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final List<String> temperatureList;
    private final List<String> daysList;
    private final CustomItemClickListener mClickListener;
    private final String timeZone;

    public MyRecyclerViewAdapter(List<String> temperature,List<String> days, String timeZone, CustomItemClickListener listener) {
        temperatureList = temperature;
        mClickListener = listener;
        daysList = days;
        this.timeZone = timeZone;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == R.layout.days_row) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_row, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button, parent, false);
        }

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int height = view.getMeasuredHeight();
        return new ViewHolder(view, height);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == temperatureList.size()) {
            holder.button.setOnClickListener(mClickListener::onItemClick);
        } else {
            holder.myTextViewDays.setText(convertTime(daysList.get(position),timeZone));
            holder.myTextViewTemperature.setText(temperatureList.get(position));
            holder.imageView.setImageResource(R.drawable.temp);
        }

    }

    @Override
    public int getItemCount() {
        return temperatureList.size()+1;
    }

    @Override
    public int getItemViewType(final int position) {
        return (position == temperatureList.size()) ? R.layout.button : R.layout.days_row;
    }

    private String convertTime(String time,String timeZone) {
        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        final long unixTime = Long.parseLong(time);
        return Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of(timeZone))
                .format(formatter);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTextViewTemperature;
        TextView myTextViewDays;
        ImageView imageView;
        Button button;

        ViewHolder(View itemView, int height) {
            super(itemView);
            itemView.setMinimumHeight(height);
            myTextViewTemperature = itemView.findViewById(R.id.Temperature_days);
            myTextViewDays = itemView.findViewById(R.id.dzien);
            imageView = itemView.findViewById(R.id.WeatherIconDays);
            button = itemView.findViewById(R.id.back);
        }
    }

}