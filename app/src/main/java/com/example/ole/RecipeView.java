package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeView extends AppCompatActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextView recipeNameTextView = (TextView) findViewById(R.id.recipeNameTextView);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> items = bundle.getStringArrayList("sItems");

        if(items != null){
            recipeNameTextView.setText(items.get(0));
        }
    }
}