package com.example.ole;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ole.dao.ShoppingListDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.roomsitems.RoomShoppingListItem;
import com.example.ole.viewmodel.ShoppingListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    bottomNavigationView = findViewById(R.id.bottomNavigationView);
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
    String recipe = getIntent().getStringExtra("recipe");
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
        intentHome.putExtra("recipe",recipe);
        startActivity(intentHome);
        overridePendingTransition(0,0);
        break;

      case R.id.bottom_menu_button_favorites:
        Intent intentFavorites = new Intent(ShoppingCartView.this, FavoritesView.class);
        intentFavorites.putExtra("HomeState",homeState);
        intentFavorites.putExtra("category", category);
        intentFavorites.putExtra("recipe",recipe);
        startActivity(intentFavorites);
        overridePendingTransition(0,0);
        break;

      case R.id.bottom_menu_button_cart:
        return true;

      case R.id.bottom_menu_button_settings:
        Intent intentSettings = new Intent(ShoppingCartView.this, FiltersView.class);
        intentSettings.putExtra("HomeState",homeState);
        intentSettings.putExtra("category", category);
        intentSettings.putExtra("recipe",recipe);
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

        Button itemDeleteButton = view.findViewById(R.id.shoppingItemDeleteButton);

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

    ListView shoppingItemsListView = findViewById(R.id.shoppingItemsList);
    shoppingItemsListView.setAdapter(simpleAdapter);
  }
}