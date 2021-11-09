package com.example.ole.model;

public class Suggestions {
    private Recipe firstSuggestion;
    private Recipe secondSuggestion;
    private int totalFetchedRecipes;
    private int totalSuggestedRecipes;
    private boolean noRecipes;

    public Suggestions() {
        this.totalFetchedRecipes = 0;
        this.totalSuggestedRecipes = 0;
        this.noRecipes = true;
    }

    public Recipe getFirstSuggestion() {
        return firstSuggestion;
    }

    public void setFirstSuggestion(Recipe firstSuggestion) {
        this.firstSuggestion = firstSuggestion;
    }

    public Recipe getSecondSuggestion() {
        return secondSuggestion;
    }

    public void setSecondSuggestion(Recipe secondSuggestion) {
        this.secondSuggestion = secondSuggestion;
    }

    public int getTotalFetchedRecipes() {
        return totalFetchedRecipes;
    }

    public void setTotalFetchedRecipes(int totalFetchedRecipes) {
        this.totalFetchedRecipes = totalFetchedRecipes;
    }

    public int getTotalSuggestedRecipes() {
        return totalSuggestedRecipes;
    }

    public void setTotalSuggestedRecipes(int totalSuggestedRecipes) {
        this.totalSuggestedRecipes = totalSuggestedRecipes;
    }

    public boolean isNoRecipes() {
        return noRecipes;
    }

    public void setNoRecipes(boolean noRecipes) {
        this.noRecipes = noRecipes;
    }
}
