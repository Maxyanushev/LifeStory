package com.example.nana.ui.replenishment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReplenishmentViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ReplenishmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("It`s replenishment fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
