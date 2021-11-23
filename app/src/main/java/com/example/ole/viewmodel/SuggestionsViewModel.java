package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.ole.model.Filter;
import com.example.ole.model.FilterType;
import com.example.ole.model.Recipe;
import com.example.ole.model.SearchFilters;
import com.example.ole.model.Suggestions;
import com.example.ole.repository.RecipesRepository;
import com.example.ole.repository.SavedDataRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SuggestionsViewModel extends AndroidViewModel {

    private final RecipesRepository recipesRepository;
    private final SavedDataRepository savedDataRepository;

    private final LiveData<List<Recipe>> fetchedRecipes;
    private final MediatorLiveData<Suggestions> suggestions = new MediatorLiveData<>();
    private final MediatorLiveData<List<Filter>> filters = new MediatorLiveData<>();
    private final String selectedCategory;

    public SuggestionsViewModel(@NonNull Application application, String category) {
        super(application);
        selectedCategory = category;

        savedDataRepository = new SavedDataRepository(application);

        filters.setValue(savedDataRepository.getFilters().getValue()); // Set initial value
        filters.addSource(savedDataRepository.getFilters(), filtersFromDb -> {
            filters.setValue(filtersFromDb);
            fetchMoreRecipes();
        });

        recipesRepository = new RecipesRepository(application, getSearchFilters());
        fetchedRecipes = recipesRepository.getRecipes();

        suggestions.addSource(fetchedRecipes, recipes -> {
            suggestions.setValue(updateSuggestions(0, recipes.size()));
        });
    }

    private SearchFilters getSearchFilters() {
        SearchFilters searchFilters = new SearchFilters(null, null, null, null, null, null);
        searchFilters.setCuisineType(selectedCategory);
        searchFilters.setDiet( getFilterNamesByType(FilterType.DIET) );
        searchFilters.setHealth( getFilterNamesByType(FilterType.HEALTH) );
        searchFilters.setMealType( getFilterNamesByType(FilterType.MEAL_TYPE) );
        searchFilters.setDishType( getFilterNamesByType(FilterType.DISH_TYPE) );
        searchFilters.setExcluded( getFilterNamesByType(FilterType.EXCLUDED) );
        return searchFilters;
    }

    private List<String> getFilterNamesByType(FilterType type) {
        if (filters.getValue() == null) {
            return Collections.emptyList();
        } else {
            return filters.getValue().stream()
                    .filter(f -> type.equals(f.getFilterType()))
                    .map(f -> f.getFilterName())
                    .collect(Collectors.toList());
        }
    }

    public void nextSuggestions() {
        int totalSuggested = suggestions.getValue().getTotalSuggestedRecipes();
        int totalFetched = suggestions.getValue().getTotalFetchedRecipes();

        if (totalSuggested >= totalFetched) {
            fetchMoreRecipes();
        } else {
            suggestions.setValue(updateSuggestions(totalSuggested, totalFetched));
        }
    }

    private Suggestions updateSuggestions(int totalSuggested, int totalFetched) {
        Suggestions newSuggestions = new Suggestions();
        newSuggestions.setNoRecipes(totalFetched == 0);
        newSuggestions.setTotalFetchedRecipes(totalFetched);

        if (totalFetched == totalSuggested+1) {
            newSuggestions.setFirstSuggestion(fetchedRecipes.getValue().get(totalSuggested));
            newSuggestions.setTotalSuggestedRecipes(totalSuggested+1);
        } else if (totalFetched >= 2) {
            newSuggestions.setFirstSuggestion(fetchedRecipes.getValue().get(totalSuggested));
            newSuggestions.setSecondSuggestion(fetchedRecipes.getValue().get(totalSuggested+1));
            newSuggestions.setTotalSuggestedRecipes(totalSuggested+2);
        }
        return newSuggestions;
    }

    private void fetchMoreRecipes() {
        recipesRepository.fetchRecipes(getSearchFilters());
    }

    public LiveData<Suggestions> getSuggestions() {
        return suggestions;
    }
}
