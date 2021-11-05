package com.example.ole.components;

public class RandomItem {

    public String getmRndCategory() {
        return mRndCategory;
    }

    public void setmRndCategory(String mRndCategory) {
        this.mRndCategory = mRndCategory;
    }

    public String getmRndImage() {
        return mRndImage;
    }

    public void setmRndImage(String mRndImage) {
        this.mRndImage = mRndImage;
    }

    public String mRndCategory = "";
    public String mRndImage = "";
    public String mRndCalorie = "";

    public RandomItem(String aRndCategory, String aRndCategoryImage){
        mRndCategory = aRndCategory;
        mRndImage = aRndCategoryImage;
    }
}
