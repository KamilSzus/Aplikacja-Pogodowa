package com.example.aplikacja_pogodowa;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;

public class MainActivity extends AppCompatActivity implements VolleyCallback {

    private final long oneHour = 3600000;
    private final int  INTERNET = 3;
    private TextView city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addCity = findViewById(R.id.AddCity);
        Button options = findViewById(R.id.Options);
        city = findViewById(R.id.City);

        checkPermission(Manifest.permission.INTERNET, INTERNET);

        addCity.setOnClickListener(v -> replaceFragment(new CityFragment(),null));
        options.setOnClickListener(v -> replaceFragment(new SettingsFragment(),null));

    //    Bundle jsonBundle = new Bundle();
        String result = readFile(getApplicationContext());
    //    if (result != null) {
    //        jsonBundle.putString("JsonWeather", result);
    //        getSupportFragmentManager().setFragmentResult("JsonWeather", jsonBundle);
    //    }
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
        DownloadFile downloadFile = new DownloadFile(getApplicationContext(),this);
        downloadFile.start(city.getText().toString());
    }

    public void checkPermission(String permission, int requestCode) {
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

    private void replaceFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainLayout, fragment)
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public void onSuccessResponse(WeatherData result) {
        DownloadImage downloadImage = new DownloadImage(getApplicationContext(),this,result);
        downloadImage.start();
    }

    private void startNewFragment(Fragment dayFragment, WeatherData result) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("WeatherData",result);
        replaceFragment(dayFragment,bundle);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onSuccessResponseImage(WeatherData result) {
        startNewFragment(new DayFragment(),result);
    }

    @Override
    public void onErrorResponseImage(VolleyError error) {
        System.out.println(error);
    }
}