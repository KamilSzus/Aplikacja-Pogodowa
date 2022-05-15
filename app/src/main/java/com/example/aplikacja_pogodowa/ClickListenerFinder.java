package com.example.aplikacja_pogodowa;

import android.view.View;

public interface ClickListenerFinder {
    void onClickTrash(int position);
    void onClickAlreadyAdded(int position);
    void onClickApply(int position, String city);
    void onClickAddToFavorite(int position, String city);
}
