package com.example.ole.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class Utility {

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] imageData) {
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
    }

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