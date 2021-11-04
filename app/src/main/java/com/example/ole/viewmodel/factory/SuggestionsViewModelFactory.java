package com.example.ole.viewmodel.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ole.viewmodel.SuggestionsViewModel;

public class SuggestionsViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final String category;

    public SuggestionsViewModelFactory(Application application, String category) {
        this.application = application;
        this.category = category;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SuggestionsViewModel(application, category);
    }
}
