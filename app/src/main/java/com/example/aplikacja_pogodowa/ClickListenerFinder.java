package com.example.aplikacja_pogodowa;

public interface ClickListenerFinder {
    void onClickTrash(int position);
    void onClickAlreadyAdded(int position);
    void onClickApply(int position, String city);
    void onClickAddToFavorite(int position, String city);
}
