package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ole.dao.IngredientDao;
import com.example.ole.dao.RecipeDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.model.Ingredient;
import com.example.ole.model.Recipe;
import com.example.ole.roomsitems.RoomIngredient;
import com.example.ole.roomsitems.RoomRecipe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class RecipeView extends AppCompatActivity {

    private static List<String> ingredientsList = new ArrayList<String>();
    public String recipeUrl;
    private RoomRecipe roomsRecipe;
    private RoomIngredient roomIngredient;

    // tarjoilee daot
    AppDatabase appDatabase;

    // rajapinta roomsiin
    RecipeDao recipeDao;

    IngredientDao ingredientDao;
    
    // roomsItems
    // recipeWith ingredients

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

        appDatabase = AppDatabase.getInstance(this);
        recipeDao  = appDatabase.getRecipeDao();
        ingredientDao = appDatabase.getIngredientDao();
        roomsRecipe = new RoomRecipe();

        recipeUrl = recipe.getUrl();
        roomsRecipe.setImageUrl(recipe.getUrl());
        roomsRecipe.setName(recipe.getLabel());
        roomsRecipe.setPreparationTime(recipe.getTotalTime());
        roomsRecipe.setRecipeUrl(recipe.getUrl());

        //recipeDao.deleteIt();

        TextView recipeNameTextView = findViewById(R.id.recipeNameTextView);
        recipeNameTextView.setText(getString(R.string.recipe_name_value, recipe.getLabel()));

        ImageView recipeImageView = findViewById(R.id.recipeImageView);
        recipeImageView.setImageBitmap(recipe.getImage());

        TextView recipeTotalTime = findViewById(R.id.timeTextNumberView);
        recipeTotalTime.setText(recipe.getTotalTime());

        createListView(recipe.getIngredients());
    }

    private void createListView(List<Ingredient> ingredients) {

        ListView listView = new ListView(this);
        ArrayAdapter adapter;
        listView = findViewById(R.id.ingredients_list_view);

        for(int i = 0; i < ingredients.size(); i++){
            ingredientsList.add(ingredients.get(i).getText());
        }

        adapter = new ArrayAdapter(RecipeView.this,
                android.R.layout.simple_list_item_1,ingredientsList);

        listView.setAdapter(adapter);
    }

    public void onClickUrl(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(recipeUrl));
        startActivity(i);
    }

    public void addToFav(View view) {

        roomsRecipe.setFavourite(true);
        long recipeID = recipeDao.insertOne(roomsRecipe);

        for (int j = 0; j < ingredientsList.size(); j++) {
            roomIngredient = new RoomIngredient();
            roomIngredient.setFk_recipe(recipeID);
            roomIngredient.setName(ingredientsList.get(j));
            ingredientDao.insertAll(roomIngredient);
        }
    }
}