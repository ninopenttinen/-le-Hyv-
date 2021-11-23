package com.example.ole.common;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.ole.R;

public class Animations {

    public static void slide_down(Context ctx, View v) {
        android.view.animation.Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static void slide_up(Context ctx, View v) {
        android.view.animation.Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

}
