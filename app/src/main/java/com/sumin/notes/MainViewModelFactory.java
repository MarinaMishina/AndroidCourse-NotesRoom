package com.sumin.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    //private NotesDatabase database;
    //private LiveData<List<Note>> notes;


    public MainViewModelFactory(Application mApplication) {
        this.mApplication = mApplication;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(mApplication);
    }
}
