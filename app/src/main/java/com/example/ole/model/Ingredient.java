package com.example.ole.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Parcel
public class Ingredient {
    String text;
    String measure;
    Double quantity;
    String name;

    @ParcelConstructor
    public Ingredient(String text, String measure, Double quantity, String name) {
        this.text = text;
        this.measure = measure;
        this.quantity = quantity;
        this.name = name;
    }
}
