package com.example.ole;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ole.model.Recipe;
import com.example.ole.viewmodel.ShoppingListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCartView extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
  private final List<HashMap<String, String>> shoppingItemHashMapList = new ArrayList<>();
  private SimpleAdapter simpleAdapter;
  private ShoppingListViewModel shoppingListViewModel;

  BottomNavigationView bottomNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cart);

    shoppingListViewModel = new ViewModelProvider(
        this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
    ).get(ShoppingListViewModel.class);

    initializeListView();

    bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    bottomNavigationView.setOnNavigationItemSelectedListener(ShoppingCartView.this);
    bottomNavigationView.setSelectedItemId(R.id.bottom_menu_button_cart);
  }

  @Override
  public void onBackPressed() {
    // TODO Auto-generated method stub
    super.onBackPressed();
    overridePendingTransition(0,0);
  }

  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    String homeState = getIntent().getStringExtra("HomeState");
    String category = getIntent().getStringExtra("category");
    Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
    switch (item.getItemId()) {
      case R.id.bottom_menu_button_home:
        Intent intentHome;
        if (homeState.equals("categoryView")){
          intentHome = new Intent(ShoppingCartView.this, CategoryView.class);
        }
        else if (homeState.equals("suggestionsView")){
          intentHome = new Intent(ShoppingCartView.this, SuggestionsView.class);
        }
        else if (homeState.equals("recipeView")){
          intentHome = new Intent(ShoppingCartView.this, RecipeView.class);
        }
        else{
          return true;
        }
        intentHome.putExtra("category", category);
        intentHome.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(intentHome);
        overridePendingTransition(0,0);
        break;

      case R.id.bottom_menu_button_favorites:
        Intent intentFavorites = new Intent(ShoppingCartView.this, FavoritesView.class);
        intentFavorites.putExtra("HomeState",homeState);
        intentFavorites.putExtra("category", category);
        intentFavorites.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(intentFavorites);
        overridePendingTransition(0,0);
        break;

      case R.id.bottom_menu_button_cart:
        return true;

      case R.id.bottom_menu_button_filters:
        Intent intentSettings = new Intent(ShoppingCartView.this, FiltersView.class);
        intentSettings.putExtra("HomeState",homeState);
        intentSettings.putExtra("category", category);
        intentSettings.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(intentSettings);
        overridePendingTransition(0,0);
        break;
    }
    return false;
  }

  private void initializeListView() {
    final String adapterIngredientName = "ingredientName";

    List<String> ingredientsList = shoppingListViewModel.getAllIngredients();

    for (String ingredientString : ingredientsList) {
      HashMap<String, String> ingredientItemHash = new HashMap<>();
      ingredientItemHash.put(adapterIngredientName, ingredientString);
      shoppingItemHashMapList.add(ingredientItemHash);
    }

    simpleAdapter = new SimpleAdapter(
        getApplicationContext(),
        shoppingItemHashMapList,
        R.layout.shopping_items_list,
        new String[]{adapterIngredientName},
        new int[]{R.id.shoppingItemName}
    ) {
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        ImageButton itemDeleteButton = view.findViewById(R.id.shoppingItemDeleteButton);

        itemDeleteButton.setOnClickListener(arg0 -> {
          shoppingListViewModel.removeIngredientFromShoppingList(
              shoppingItemHashMapList.get(position).get(adapterIngredientName)
          );
          shoppingItemHashMapList.remove(position);
          simpleAdapter.notifyDataSetChanged();
        });

        return view;
      }
    };

    ListView shoppingItemsListView = findViewById(R.id.shopping_items_list);
    shoppingItemsListView.setAdapter(simpleAdapter);
  }
}