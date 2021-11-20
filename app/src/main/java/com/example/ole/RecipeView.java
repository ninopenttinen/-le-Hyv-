package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.ole.viewmodel.RecipeViewModel;
import com.example.ole.viewmodel.SuggestionsViewModel;
import com.example.ole.viewmodel.factory.SuggestionsViewModelFactory;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RecipeView extends AppCompatActivity {

    private RecipeViewModel recipeViewModel;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

        recipeViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(RecipeViewModel.class);

        TextView recipeNameTextView = findViewById(R.id.recipeNameTextView);
        recipeNameTextView.setText(getString(R.string.recipe_name_value, recipe.getLabel()));

        ImageView recipeImageView = findViewById(R.id.recipeImageView);
        recipeImageView.setImageBitmap(recipe.getImage());

        TextView recipeTotalTime = findViewById(R.id.timeTextNumberView);
        recipeTotalTime.setText(recipe.getTotalTime());

        createListView(recipe.getIngredients());
    }

    private void createListView(List<Ingredient> ingredients) {
        ListView listView;
        ArrayAdapter adapter;
        listView = findViewById(R.id.ingredients_list_view);

        adapter = new ArrayAdapter(RecipeView.this,
                android.R.layout.simple_list_item_1,
                ingredients.stream()
                        .map(i -> i.getText())
                        .collect(Collectors.toList()));

        listView.setAdapter(adapter);
    }

    public void onClickUrl(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(recipe.getUrl()));
        startActivity(i);
    }

    public void addToFav(View view) {
        recipeViewModel.addRecipeToFavourites(recipe);
    }
}