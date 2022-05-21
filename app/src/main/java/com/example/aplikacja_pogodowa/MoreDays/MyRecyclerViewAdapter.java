package com.example.aplikacja_pogodowa.MoreDays;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.aplikacja_pogodowa.Download.WeatherData;
import com.example.aplikacja_pogodowa.R;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final CustomItemClickListener mClickListener;
    private final WeatherData weatherData;

    public MyRecyclerViewAdapter(WeatherData weatherData, CustomItemClickListener listener) {
        this.weatherData = weatherData;
        mClickListener = listener;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == weatherData.getTemperatureList().size()) {
            holder.button.setOnClickListener(mClickListener::onItemClick);
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            holder.myTextViewDays.setText(convertTime(weatherData.getDayList().get(position), weatherData.getTimeZone()));
            holder.myTextViewTemperature.setText(df.format(weatherData.getTemperatureList().get(position)));
            holder.networkImageView.setDefaultImageBitmap(weatherData.getImageList().get(position+1));
        }

    }

    @Override
    public int getItemCount() {
        return weatherData.getTemperatureList().size()+1;
    }

    @Override
    public int getItemViewType(final int position) {
        return (position == weatherData.getTemperatureList().size()) ? R.layout.button : R.layout.days_row;
    }

    private String convertTime(Integer time, String timeZone) {
        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        final long unixTime = Long.parseLong(String.valueOf(time));
        return Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of(timeZone))
                .format(formatter);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTextViewTemperature;
        TextView myTextViewDays;
        NetworkImageView networkImageView;
        Button button;

        ViewHolder(View itemView, int height) {
            super(itemView);
            itemView.setMinimumHeight(height);
            myTextViewTemperature = itemView.findViewById(R.id.Temperature_days);
            myTextViewDays = itemView.findViewById(R.id.dzien);
            networkImageView = itemView.findViewById(R.id.WeatherIconDays);
            button = itemView.findViewById(R.id.back);
        }
    }

}