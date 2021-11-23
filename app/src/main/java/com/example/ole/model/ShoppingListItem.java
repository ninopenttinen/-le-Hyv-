package com.example.ole.model;

import android.graphics.Bitmap;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

import lombok.Data;


@Parcel
@Data
public class ShoppingListItem {

  String name;
  Double amount;

   @ParcelConstructor
   public ShoppingListItem(String name, Double amount) {
    this.name = name;
    this.amount = amount;
  }

}
