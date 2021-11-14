package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ole.model.Filter;
import com.example.ole.model.FilterType;

import java.util.ArrayList;
import java.util.List;

public class FiltersViewModel extends AndroidViewModel {

    // TODO: convert to not mutable
    private final MutableLiveData<List<Filter>> filters;

    List<Filter> filters_PLACEHOLDER = new ArrayList<>();

    public FiltersViewModel(@NonNull Application application) {
        super(application);

        filters = new MutableLiveData<>();

        // TODO: replace placeholder data with real data from db
        filters_PLACEHOLDER.add(new Filter(FilterType.EXCLUDED, "peanuts"));
        filters_PLACEHOLDER.add(new Filter(FilterType.EXCLUDED, "fish"));
        filters_PLACEHOLDER.add(new Filter(FilterType.EXCLUDED, "chicken"));

        filters.setValue(filters_PLACEHOLDER);
    }

    public LiveData<List<Filter>> getFilters() {
        return filters;
    }

    public void addFilter(Filter filter) {
        filters_PLACEHOLDER.add(filter);
        filters.setValue(filters_PLACEHOLDER);
    }

    public void removeExclusionFilter(String filterName) {
        filters_PLACEHOLDER.removeIf(f ->
                f.getFilterType().equals(FilterType.EXCLUDED) && f.getFilterName().equals(filterName)
        );
        filters.setValue(filters_PLACEHOLDER);
    }

    public void removeFilter(String filterName) {
        filters_PLACEHOLDER.removeIf(f ->
                !f.getFilterType().equals(FilterType.EXCLUDED) && f.getFilterName().equals(filterName)
        );
        filters.setValue(filters_PLACEHOLDER);
    }
}
