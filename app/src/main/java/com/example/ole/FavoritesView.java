package com.example.ole;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ole.components.FavoriteRecipe;
import com.example.ole.model.Recipe;
import com.example.ole.viewmodel.FavoritesViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavoritesView extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

  FavoritesViewModel favoritesViewModel;

  BottomNavigationView bottomNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favorites);

    favoritesViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
        .get(FavoritesViewModel.class);

    favoritesViewModel.getRecipes().observe(this, this::updateFavorites);

    bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    bottomNavigationView.setOnNavigationItemSelectedListener(FavoritesView.this);
    bottomNavigationView.setSelectedItemId(R.id.bottom_menu_button_favorites);
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
    //String recipe = getIntent().getStringExtra("recipe");
    Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

    switch (item.getItemId()) {
      case R.id.bottom_menu_button_home:
        Intent intentHome;
        if (homeState.equals("categoryView")){
          intentHome = new Intent(FavoritesView.this, CategoryView.class);
        }
        else if (homeState.equals("suggestionsView")){
          intentHome = new Intent(FavoritesView.this, SuggestionsView.class);
        }
        else if (homeState.equals("recipeView")){
          intentHome = new Intent(FavoritesView.this, RecipeView.class);
        }
        else {
          return false;
        }
        intentHome.putExtra("category", category);
        intentHome.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(intentHome);
        overridePendingTransition(0,0);
        break;

      case R.id.bottom_menu_button_favorites:
        return true;

      case R.id.bottom_menu_button_cart:
        Intent intentCart = new Intent(FavoritesView.this, ShoppingCartView.class);
        intentCart.putExtra("HomeState",homeState);
        intentCart.putExtra("category", category);
        intentCart.putExtra("recipe", Parcels.wrap(recipe));

        startActivity(intentCart);
        overridePendingTransition(0,0);
        break;

      case R.id.bottom_menu_button_filters:
        Intent intentSettings = new Intent(FavoritesView.this, FiltersView.class);
        intentSettings.putExtra("HomeState",homeState);
        intentSettings.putExtra("category", category);
        intentSettings.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(intentSettings);
        overridePendingTransition(0,0);
        break;
    }
    return false;
  }


  private void updateFavorites(List<Recipe> favRecipes) {
    List<HashMap<String, Object>> favItemHashMapList = new ArrayList<>();
    List<FavoriteRecipe> favItemArrayList = new ArrayList<>();

    for (int i = 0; i < favRecipes.size(); i++) {
      favItemArrayList.add(new FavoriteRecipe(
          favRecipes.get(i).getLabel(),
          favRecipes.get(i).getImage()
      ));
    }

    for (FavoriteRecipe i : favItemArrayList) {
      HashMap<String, Object> favoriteItemHash = new HashMap<>();
      favoriteItemHash.put("favName", i.mFavoriteRecipe);
      favoriteItemHash.put("favImg", i.mFavoriteImage);
      favItemHashMapList.add(favoriteItemHash);
    }

    SimpleAdapter simpleAdapter = new SimpleAdapter(this, favItemHashMapList, R.layout.favorites_list_view,
        new String[]{"favImg", "favName"},
        new int[]{R.id.fav_list_img_view, R.id.fav_label_view}
    );

    simpleAdapter.setViewBinder((view, data, textRepresentation) -> {
      if ( view.getId() == R.id.fav_list_img_view && data instanceof Bitmap ) {
        ImageView imageView = (ImageView) view;
        Bitmap bitmap = (Bitmap) data;
        imageView.setImageBitmap(bitmap);
        return true;
      }
      return false;
    });

    ListView favListView = findViewById(R.id.favorites_list_view);
    favListView.setAdapter(simpleAdapter);

    favListView.setOnItemClickListener((parent, view, position, id) -> {
      Intent intent = new Intent(this, RecipeView.class);
      intent.putExtra("recipe", Parcels.wrap(favRecipes.get(position)));
      startActivity(intent);
    });

    favListView.setOnItemLongClickListener((parent, view, position, id) -> {

      Recipe recipeToBeRemoved = new Recipe(
          null,
          null,
          favRecipes.get(position).getUrl(),
          null,
          null,
          null);

      BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FavoritesView.this, R.style.BottomSheetDialogTheme);
      View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_popup_layout,
          findViewById(R.id.bottomSheetContainer));

      bottomSheetDialog.setContentView(bottomSheetView);
      bottomSheetDialog.show();

      bottomSheetView.findViewById(R.id.cancel_button).setOnClickListener(v1 -> {
        Toast.makeText(FavoritesView.this, "Canceled", Toast.LENGTH_LONG).show();
        bottomSheetDialog.dismiss();
      });

      bottomSheetView.findViewById(R.id.confirm_button).setOnClickListener(v1 -> {
        removeFromFav(recipeToBeRemoved);
        Toast.makeText(FavoritesView.this, "Removed from favorites", Toast.LENGTH_LONG).show();
        bottomSheetDialog.dismiss();
      });
      return true;
    });
  }

  public void removeFromFav(Recipe recipe) {
    favoritesViewModel.removeRecipeFromFavourites(recipe);
  }
}