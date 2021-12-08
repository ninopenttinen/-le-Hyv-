package com.example.ole;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.ole.components.CategoryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryView extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
  private final List<CategoryItem> categoryItemArrayList = new ArrayList<CategoryItem>();
  private List<HashMap<String, String>> categoryItemHashMapList = new ArrayList<>();
  private SimpleAdapter simpleAdapter;

  BottomNavigationView bottomNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category);

    setCategoryView();

    bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    bottomNavigationView.setOnNavigationItemSelectedListener(CategoryView.this);
    bottomNavigationView.setSelectedItemId(R.id.bottom_menu_button_home);
  }

  @Override
  public void onBackPressed() {
    // TODO Auto-generated method stub
    super.onBackPressed();
    overridePendingTransition(0,0);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.bottom_menu_button_home:
        return true;

      case R.id.bottom_menu_button_favorites:
        Intent intentFavorites = new Intent(CategoryView.this, FavoritesView.class);
        intentFavorites.putExtra("HomeState", "categoryView");
        startActivity(intentFavorites);
        overridePendingTransition(0,0);
        break;

      case R.id.bottom_menu_button_cart:
        Intent intentCart = new Intent(CategoryView.this, ShoppingCartView.class);
        intentCart.putExtra("HomeState", "categoryView");
        startActivity(intentCart);
        overridePendingTransition(0,0);
        break;

      case R.id.bottom_menu_button_filters:
        Intent intentSettings = new Intent(CategoryView.this, FiltersView.class);
        intentSettings.putExtra("HomeState", "categoryView");
        startActivity(intentSettings);
        overridePendingTransition(0,0);
        break;
    }
    return false;
  }

  private void setCategoryView() {
    int categorySize = getResources().getStringArray(R.array.cuisine_types_string).length;

    for (int i = 0; i < categorySize; i++) {
      String categoryImage = Integer.toString(
          getResources()
              .obtainTypedArray(R.array.cuisine_images_ref)
              .getResourceId(i, 0)
      );
      String categoryName = getResources().getStringArray(R.array.cuisine_types_string)[i];

      categoryItemArrayList.add(new CategoryItem(categoryName, categoryImage));
    }

    for (CategoryItem i : categoryItemArrayList) {
      HashMap<String, String> categoryItemHash = new HashMap<>();
      categoryItemHash.put("categoryImage", i.categoryImage);
      categoryItemHash.put("categoryName", i.categoryName);
      categoryItemHashMapList.add(categoryItemHash);
    }

    simpleAdapter = new SimpleAdapter(this, categoryItemHashMapList, R.layout.category_grid_layout,
        new String[]{"categoryImage", "categoryName"},
        new int[]{R.id.category_image_view, R.id.category_text_view}
    );

    GridView categoryGridView = (GridView) findViewById(R.id.gridView);
    categoryGridView.setAdapter(simpleAdapter);

    categoryGridView.setOnItemClickListener((parent, view, position, id) -> {
      String category = getResources().getStringArray(R.array.cuisine_types_string)[position];

      Intent intent = new Intent(this, SuggestionsView.class);
      intent.putExtra("category", category);
      startActivity(intent);
      overridePendingTransition(0, 0);
    });
  }

  public void settingsOnClick(View view) {
    Intent intent = new Intent(this, FiltersView.class);
    startActivity(intent);
    overridePendingTransition(0, 0);
  }

  public void cartOnClick(View view) {
    Intent intent = new Intent(this, ShoppingCartView.class);
    startActivity(intent);
    overridePendingTransition(0, 0);
  }

  public void favoritesOnClick(View view) {
    Intent intent = new Intent(this, FavoritesView.class);
    startActivity(intent);
  }
}