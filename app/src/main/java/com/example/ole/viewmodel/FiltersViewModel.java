package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ole.model.Filter;
import com.example.ole.model.FilterType;
import com.example.ole.repository.SavedDataRepository;

import java.util.ArrayList;
import java.util.List;

public class FiltersViewModel extends AndroidViewModel {

    private final SavedDataRepository savedDataRepository;

    private final LiveData<List<Filter>> fromDatabase;
    private final MediatorLiveData<List<Filter>> filters = new MediatorLiveData<>();


    public FiltersViewModel(@NonNull Application application) {
        super(application);
        savedDataRepository = new SavedDataRepository(application);
        fromDatabase = savedDataRepository.getFilters();
        filters.addSource(fromDatabase, filters::setValue);
    }

    public LiveData<List<Filter>> getFilters() {
        return filters;
    }

    public void addFilter(Filter filter) {
        savedDataRepository.addFilter(filter);
    }

    public void removeFilter(Filter filter) {
        savedDataRepository.removeFilter(filter);
    }
}
