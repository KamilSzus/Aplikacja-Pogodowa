package com.example.aplikacja_pogodowa;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.VolleyError;
import com.example.aplikacja_pogodowa.Download.DownloadFile;
import com.example.aplikacja_pogodowa.Download.DownloadImage;
import com.example.aplikacja_pogodowa.Download.VolleyCallback;
import com.example.aplikacja_pogodowa.Download.WeatherData;
import com.example.aplikacja_pogodowa.Fragments.ChangeUnits;
import com.example.aplikacja_pogodowa.MVVM.ViewModel;
import com.example.aplikacja_pogodowa.MVVM.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements VolleyCallback {

    private final int INTERNET = 3;
    private TextView city;
    private WeatherData weatherData;
    private ViewModel model;
    private String units;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.City);
        checkPermission(Manifest.permission.INTERNET, INTERNET);
        model = new ViewModelProvider(this).get(ViewModel.class);


        DownloadFile downloadFile = new DownloadFile(getApplicationContext(), this);
        String result = readFile();

        if (result != null) {
            WeatherData data = downloadFile.setData(result);
            city.setText(data.getCityName());
            DownloadImage downloadImage = new DownloadImage(getApplicationContext(), this, data);
            downloadImage.start();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        units = prefs.getString("Skala_termometryczna","Kelvin");


        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 1:
                    tab.setText("Dzisiaj");
                    break;
                case 2:
                    tab.setText("Wiele dni");
                    break;
                case 3:
                    tab.setText("Wi??cej informacji");
                    break;
                default:
                    tab.setText("Wyszukiwarka");
                    break;
            }
        }).attach();
    }

    public void setTextViewCity(String newName) {
        city.setText(newName);
    }

    public String getTextViewCity() {
        return city.getText().toString();
    }

    public WeatherData getWeatherData(){
        return weatherData;
    }

    public String readFile() {
        try {
            File file = new File(getApplicationContext().getFilesDir(), "Weather.Json");
            if (file.exists()) {
                long oneHour = 3600000;
                if (file.lastModified() + oneHour > System.currentTimeMillis()) {
                    FileInputStream fileInputStream = getApplicationContext().openFileInput("Weather.Json");
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    bufferedReader.lines().forEach(stringBuilder::append);
                    return stringBuilder.toString();
                }
            }
        } catch (IOException fileNotFound) {
            createFile();
            return null;
        }
        createFile();
        return null;
    }

    public void changeUnits() {
        if(weatherData!=null) {
            ChangeUnits units = new ChangeUnits(weatherData);
            units.run();
            model.setWeatherData(weatherData);
        }
    }

    public void createFile() {
        DownloadFile downloadFile = new DownloadFile(getApplicationContext(), this);
        downloadFile.downloadNewData(city.getText().toString());
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == INTERNET) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Internet Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Internet Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessResponse(WeatherData result) {
        DownloadImage downloadImage = new DownloadImage(getApplicationContext(), this, result);
        downloadImage.start();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onSuccessResponseImage(WeatherData result) {
        weatherData = result;
        if(units.equals("Kelvin")){
            model.setWeatherData(result);
        }else{
            changeUnits();
        }
    }

    @Override
    public void onErrorResponseImage(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.Refresh){
            createFile();
        }
        return super.onOptionsItemSelected(item);
    }
}