package com.example.aplikacja_pogodowa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class DayFragment extends Fragment {

    private GestureDetector mDetector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
       mDetector = new GestureDetector(getActivity(), new SimpleOnGestureListener(){
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
                       if (diffY > 0) {
                           System.out.println("gora dol");
                           requireActivity()
                                   .getSupportFragmentManager()
                                   .beginTransaction()
                                   .replace(R.id.test,new HoursFragment())
                                   .commit();
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

        return view;
    }
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return mDetector.onTouchEvent(event);

        }
    };
}