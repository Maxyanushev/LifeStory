package com.example.nana.ui.studies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudiesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public StudiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is studies fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
