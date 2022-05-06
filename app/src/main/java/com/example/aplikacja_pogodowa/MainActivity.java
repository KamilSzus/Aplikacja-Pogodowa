package com.example.aplikacja_pogodowa;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button addCity,options;
    private final long oneHour = 3600000;
    private final int  INTERNET = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCity = findViewById(R.id.AddCity);
        options = findViewById(R.id.Options);

        checkPermission(Manifest.permission.INTERNET, INTERNET);

        addCity.setOnClickListener(v -> replaceFragment(new DayFragment()));
        options.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        });

        Bundle jsonBundle = new Bundle();
        String result = readFile(getApplicationContext());
        if (result != null) {
            jsonBundle.putString("JsonWeather", result);
            getSupportFragmentManager().setFragmentResult("JsonWeather", jsonBundle);
        }

        replaceFragment(new DayFragment());
    }

    private String readFile(Context context) {
      //  try {
      //      File file = new File(getApplicationContext().getFilesDir(), "Weather.Json");
      //      if (file.exists()) {
      //          Date lastModDate = new Date(file.lastModified());
      //          if (lastModDate.getTime() + oneHour < System.currentTimeMillis()) {
//
      //              FileInputStream fileInputStream = context.openFileInput("Weather.Json");
//
      //              InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
      //              BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      //              StringBuilder stringBuilder = new StringBuilder();
//
      //              bufferedReader.lines().forEach(stringBuilder::append);
//
      //              return stringBuilder.toString();
      //          }
      //      }
      //  } catch (IOException fileNotFound) {
      //      createFile();
      //      return null;
      //  }
      //  createFile();
      //  return null;
        createFile();
        return null;
    }

    private void createFile() {
        DownloadFile downloadFile = new DownloadFile(getApplicationContext());
        downloadFile.start("Łódź");
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
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

    private void replaceFragment(Fragment fragment) {

        getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.test,fragment)
        .commit();

    }
}