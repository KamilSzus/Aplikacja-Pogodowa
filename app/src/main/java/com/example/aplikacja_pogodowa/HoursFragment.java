package com.example.aplikacja_pogodowa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HoursFragment extends Fragment  {

    private GestureDetector mDetector;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hours, container, false);
        gesture(view);

        ArrayList<String> temperature = new ArrayList<>();
        ArrayList<String> day = new ArrayList<>();
        temperature.add("30");
        temperature.add("31");
        temperature.add("32");
        temperature.add("33");
        temperature.add("34");

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViews);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(temperature, (v) -> {
            System.out.println("ALAAAAAA");
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.test, new DayFragment())
                    .commit();
        }
        ));

        return view;
    }

    private void gesture(View view) {
        mDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onDown(MotionEvent event){
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    final int SWIPE_THRESHOLD = 100;
                    final int SWIPE_VELOCITY_THRESHOLD = 100;

                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY < 0) {
                            requireActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.test,new DayFragment())
                                    .commit();
                            System.out.println("dol");
                            result = true;
                        }
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        });
        view.setOnTouchListener(touchListener);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return mDetector.onTouchEvent(event);
        }
    };
}