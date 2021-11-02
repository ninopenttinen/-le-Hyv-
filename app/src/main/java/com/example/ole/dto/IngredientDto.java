package com.example.ole.dto;

import com.google.gson.annotations.SerializedName;

public class IngredientDto {
    @SerializedName("text")
    String text;

    @SerializedName("measure")
    String measure;

    @SerializedName("quantity")
    Double quantity;

    @SerializedName("food")
    String food;

    public String getText() {
        return text;
    }

    public String getMeasure() {
        return measure;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getFood() {
        return food;
    }
}
