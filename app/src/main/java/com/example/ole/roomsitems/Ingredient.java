package com.example.ole.roomsitems;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "ingredient_name")
    private String name;

    @ColumnInfo(name = "ingredient_amount")
    private float amount;

    public Ingredient(){};

    @Ignore
    public Ingredient(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
