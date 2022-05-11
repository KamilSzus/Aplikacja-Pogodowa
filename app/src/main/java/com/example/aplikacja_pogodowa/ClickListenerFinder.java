package com.example.aplikacja_pogodowa;

import android.view.View;

public interface ClickListenerFinder {
    void onClickTrash(int index);
    void onClickAlreadyAdded(View v);
    void onClickApply(View v);
    void onClickAddToFavorite(View v);
}
