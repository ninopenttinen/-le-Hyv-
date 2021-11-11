package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ole.model.Recipe;
import com.example.ole.roomsitems.RoomRecipe;

import org.parceler.Parcels;


public class RecipeView extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

        TextView recipeNameTextView = findViewById(R.id.recipeNameTextView);
        recipeNameTextView.setText(getString(R.string.recipe_name_value, recipe.getLabel()));

    }
}