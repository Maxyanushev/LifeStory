package com.example.nana.fragments.navigation.drawer.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public NewsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("It`s news fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}