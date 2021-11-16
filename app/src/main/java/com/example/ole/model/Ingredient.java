package com.example.ole.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Parcel
public class Ingredient {
    String name;
    Double quantity;
    String measure;
    String text;

    @ParcelConstructor
    public Ingredient(String text, String measure, Double quantity, String name) {
        this.text = text;
        this.measure = measure;
        this.quantity = quantity;
        this.name = name;
    }
}
