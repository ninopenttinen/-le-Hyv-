package com.example.ole.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;

public class Utility {

    // NOTE: Can't be ran in main thread!
    public static Bitmap loadImageAsBitmap(String imageUrl) {
        Bitmap bm = null;
        try {
            URL url = new URL(imageUrl);
            bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }
}