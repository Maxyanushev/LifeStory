package com.example.nana.ui.rave;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RaveViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public RaveViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is rave fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
