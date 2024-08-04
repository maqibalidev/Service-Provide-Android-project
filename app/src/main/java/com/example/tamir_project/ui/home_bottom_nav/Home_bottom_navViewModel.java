package com.example.tamir_project.ui.home_bottom_nav;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Home_bottom_navViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public Home_bottom_navViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}