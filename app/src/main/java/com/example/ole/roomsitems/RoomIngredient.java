package com.example.ole.roomsitems;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "ingredients")
@Data
@NoArgsConstructor
public class RoomIngredient {

    @PrimaryKey(autoGenerate = true)
    private long ingredientId;

    @ColumnInfo(name = "fk_recipe")
    private long fk_recipe;

    @ColumnInfo(name = "ingredient_name")
    private String name;

    @ColumnInfo(name = "ingredient_quantity")
    private Double quantity;

    @ColumnInfo(name = "ingredient_measure")
    private String measure;

    @ColumnInfo(name = "ingredient_text")
    private String text;

}
