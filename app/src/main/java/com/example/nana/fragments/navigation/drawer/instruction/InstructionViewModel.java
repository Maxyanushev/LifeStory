package com.example.nana.fragments.navigation.drawer.instruction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InstructionViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public InstructionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("It`s instruction fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
