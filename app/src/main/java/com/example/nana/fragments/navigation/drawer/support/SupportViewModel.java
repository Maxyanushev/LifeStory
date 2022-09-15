package com.example.nana.fragments.navigation.drawer.support;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SupportViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SupportViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("It`s support fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
