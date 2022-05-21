package com.example.aplikacja_pogodowa;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.aplikacja_pogodowa.Fragments.DayFragment;
import com.example.aplikacja_pogodowa.Fragments.MoreDetailsAboutDay;
import com.example.aplikacja_pogodowa.MoreDays.DaysFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return  new DayFragment();
            case 2:
                return  new MoreDetailsAboutDay();
            default:
                return new DaysFragment();
        }
    }
}
