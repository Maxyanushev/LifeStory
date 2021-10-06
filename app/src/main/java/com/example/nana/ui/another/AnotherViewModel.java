package com.example.nana.ui.another;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnotherViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AnotherViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is another fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
