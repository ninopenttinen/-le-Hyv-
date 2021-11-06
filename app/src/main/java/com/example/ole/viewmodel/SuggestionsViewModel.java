package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.ole.model.Recipe;
import com.example.ole.model.SearchFilters;
import com.example.ole.model.Suggestions;
import com.example.ole.repository.RecipesRepository;

import java.util.List;

public class SuggestionsViewModel extends AndroidViewModel {

    private final RecipesRepository recipesRepository;

    private LiveData<List<Recipe>> fetchedRecipes;
    private MediatorLiveData<Suggestions> suggestions = new MediatorLiveData<>();
    //private LiveData<List<searchFilters>> searchFilters; // TODO: observe and trigger recipesRepository.fetchData(); when filters change
    private SearchFilters searchFilters_PLACEHOLDER = new SearchFilters(null, null, null, null, null, null);


    public SuggestionsViewModel(@NonNull Application application, String category) {
        super(application);
        searchFilters_PLACEHOLDER.setCuisineType(category);

        recipesRepository = new RecipesRepository(application, searchFilters_PLACEHOLDER);

        fetchedRecipes = recipesRepository.getRecipes();
        suggestions.setValue(new Suggestions());

        suggestions.addSource(fetchedRecipes, recipes -> {
            suggestions.setValue(updateSuggestions(0, recipes.size()));
        });
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
        recipesRepository.fetchRecipes(searchFilters_PLACEHOLDER);
    }

    public LiveData<Suggestions> getSuggestions() {
        return suggestions;
    }
}
