package com.example.ole.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class Ingredient {
    String text;
    String measure;
    Double quantity;
    String food;

    @ParcelConstructor
    public Ingredient(String text, String measure, Double quantity, String food) {
        this.text = text;
        this.measure = measure;
        this.quantity = quantity;
        this.food = food;
    }

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

    public void setText(String text) {
        this.text = text;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
