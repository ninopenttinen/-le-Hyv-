package com.example.ole.roomsitems;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "ingredients")
@Data
@NoArgsConstructor
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private long ingredientId;

    @ColumnInfo(name = "fk_recipe")
    private long fk_recipe;

    @ColumnInfo(name = "ingredient_name")
    private String name;

    @ColumnInfo(name = "ingredient_amount")
    private float amount;


}
