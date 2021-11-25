package com.example.ole.components;

import android.graphics.Bitmap;

public class FavoriteRecipe {

    public String mFavoriteRecipe = "";
    public Bitmap mFavoriteImage;

    public FavoriteRecipe(String aFavoriteRecipe, Bitmap aFavoriteImage){
        mFavoriteRecipe = aFavoriteRecipe;
        mFavoriteImage = aFavoriteImage;
    }
}
