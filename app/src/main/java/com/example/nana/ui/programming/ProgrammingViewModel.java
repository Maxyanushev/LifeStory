package com.example.nana.ui.programming;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProgrammingViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ProgrammingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is programming fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
