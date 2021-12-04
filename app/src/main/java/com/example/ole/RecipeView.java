package com.example.ole;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ole.model.Ingredient;
import com.example.ole.model.Recipe;
import com.example.ole.viewmodel.RecipeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RecipeView extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

  BottomNavigationView bottomNavigationView;
  private List<Ingredient> ingredientsToCart;
  private RecipeViewModel recipeViewModel;
  private Recipe recipe;
  private Button cartButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_view);
    recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

    recipeViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
        .get(RecipeViewModel.class);

    initCartButton();
    initRecipeElements();
    initToggleButton();
    handleTimeElements();
    createListView(recipe.getIngredients());

    bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    bottomNavigationView.setOnNavigationItemSelectedListener(RecipeView.this);
    bottomNavigationView.setSelectedItemId(R.id.bottom_menu_button_home);
  }

  @Override
  public void onBackPressed() {
    // TODO Auto-generated method stub
    //super.onBackPressed();
    Intent backIntent = new Intent(RecipeView.this, SuggestionsView.class);
    overridePendingTransition(0, 0);
  }

  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    String category = getIntent().getStringExtra("category");
    switch (item.getItemId()) {
      case R.id.bottom_menu_button_home:
        return true;

      case R.id.bottom_menu_button_favorites:
        Intent intentFavorites = new Intent(RecipeView.this, FavoritesView.class);
        intentFavorites.putExtra("HomeState", "recipeView");
        intentFavorites.putExtra("category", category);
        intentFavorites.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(intentFavorites);
        overridePendingTransition(0, 0);
        break;

      case R.id.bottom_menu_button_cart:
        Intent intentCart = new Intent(RecipeView.this, ShoppingCartView.class);
        intentCart.putExtra("HomeState", "recipeView");
        intentCart.putExtra("category", category);
        intentCart.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(intentCart);
        overridePendingTransition(0, 0);
        break;

      case R.id.bottom_menu_button_filters:
        Intent intentSettings = new Intent(RecipeView.this, FiltersView.class);
        intentSettings.putExtra("HomeState", "RecipeView");
        intentSettings.putExtra("category", category);
        intentSettings.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(intentSettings);
        overridePendingTransition(0, 0);
        break;
    }
    return false;
  }

  private void initCartButton() {
    cartButton = findViewById(R.id.AddToCartButton);
    cartButton.setVisibility(View.GONE);
    cartButton.setTextSize(14);
  }

  private void initRecipeElements() {
    TextView recipeNameTextView = findViewById(R.id.recipeNameTextView);
    recipeNameTextView.setText(getString(R.string.recipe_name_value, recipe.getLabel()));

    ImageView recipeImageView = findViewById(R.id.recipeImageView);
    recipeImageView.setImageBitmap(recipe.getImage());
  }

  private void initToggleButton() {
    ToggleButton toggle = findViewById(R.id.addRemoveButton2);
    if (checkFavorites(recipe)) {
      toggle.setTextOff("Remove From Favorites");
      toggle.setChecked(true);
    } else {
      toggle.setTextOn("Add To Favorites");
      toggle.setChecked(false);
    }

    toggle.setOnClickListener(v -> {
      if (toggle.isChecked()) {
        toggle.setTextOn("Remove From Favorites");
        addToFav(recipe);
        toggle.setChecked(true);
      } else {
        toggle.setTextOff("Add To Favorites");
        removeFromFav(recipe);
        toggle.setChecked(false);
      }
    });
  }

  private void handleTimeElements() {
    TextView recipeTotalTime = findViewById(R.id.timeTextNumberView);
    TextView recipeTotalTimeView = findViewById(R.id.timeTextView);

    if (recipe.getTotalTime().equals("0.0")) {
      recipeTotalTime.setVisibility(View.GONE);
      recipeTotalTimeView.setVisibility(View.GONE);
    } else {
      recipeTotalTime.setText(recipe.getTotalTime());
    }
  }

  private void createListView(List<Ingredient> ingredients) {
    List<Ingredient> tempIngList = new ArrayList<>();
    ListView listView;
    ArrayAdapter adapter;
    listView = findViewById(R.id.ingredients_list_view);

    adapter = new ArrayAdapter(RecipeView.this,
        R.layout.simple_list_item_multiple_choice,
        ingredients.stream()
            .map(Ingredient::getText)
            .collect(Collectors.toList()));

    listView.setAdapter(adapter);

    listView.setOnItemClickListener((parent, view, position, id) -> {

      CheckedTextView checkedTextView = ((CheckedTextView) view);
      checkedTextView.setChecked(!checkedTextView.isChecked());

      if (checkedTextView.isChecked()) {
        tempIngList.add(ingredients.get(position));
      } else {
        tempIngList.remove(ingredients.get(position));
      }
      cartButton.setEnabled(!tempIngList.isEmpty());

      if (tempIngList.isEmpty()) {
        cartButton.setVisibility(View.GONE);
      } else {
        cartButton.setVisibility(View.VISIBLE);
      }
    });
    ingredientsToCart = tempIngList;
  }

  public void onClickUrl(View view) {
    Intent intent = new Intent(getApplicationContext(), WebViewView.class);
    intent.putExtra("url", recipe.getUrl());
    startActivity(intent);
  }

  public void AddToCart(View view) {
    recipeViewModel.addToCart(ingredientsToCart);
    Toast.makeText(RecipeView.this, "Shopping Cart Updated", Toast.LENGTH_SHORT).show();
  }

  public void addToFav(Recipe recipe) {
    recipeViewModel.addRecipeToFavourites(recipe);
  }

  public void removeFromFav(Recipe recipe) {
    recipeViewModel.removeRecipeFromFavourites(recipe);
  }

  public boolean checkFavorites(Recipe recipe) {
    return recipeViewModel.checkFavorites(recipe);
  }
}