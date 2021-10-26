package com.example.ole.model;

import java.util.List;

public class Recipe {
    String label;
    String image;
    String url;
    List<Ingredient> ingredients;
    String calories;
    String totalTime;

    public Recipe(String label, String image, String url, List<Ingredient> ingredients, String calories, String totalTime) {
        this.label = label;
        this.image = image;
        this.url = url;
        this.ingredients = ingredients;
        this.calories = calories;
        this.totalTime = totalTime;
    }

    public String getLabel() {
        return label;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getCalories() {
        return calories;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
