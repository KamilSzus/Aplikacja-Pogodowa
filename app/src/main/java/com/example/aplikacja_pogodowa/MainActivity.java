package com.example.aplikacja_pogodowa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button addCity,options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCity=findViewById(R.id.AddCity);
        options=findViewById(R.id.Options);

        addCity.setOnClickListener(v -> System.out.println("dodawanie miasta"));
        options.setOnClickListener(v -> System.out.println("opcje"));


        replaceFragment(new DayFragment());
    }


    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.test,fragment);
        fragmentTransaction.commit();

    }
}