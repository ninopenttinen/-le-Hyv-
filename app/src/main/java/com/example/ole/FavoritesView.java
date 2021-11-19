package com.example.ole;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ole.components.FavoriteRecipe;
import com.example.ole.dao.RecipeDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.model.Ingredient;
import com.example.ole.model.Recipe;
import com.example.ole.roomsitems.RoomRecipeWithIngredients;
import com.example.ole.viewmodel.FavoritesViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavoritesView extends AppCompatActivity {

  private final List<FavoriteRecipe> favItemArrayList = new ArrayList<FavoriteRecipe>();
  private final List<HashMap<String, String>> favItemHashMap = new ArrayList<>();
  FavoritesViewModel favoritesViewModel;
  AppDatabase appDatabase;
  RecipeDao recipeDao;
  List<RoomRecipeWithIngredients> recipeWithIngredients;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favorites);

    appDatabase = AppDatabase.getInstance(this);
    recipeDao = appDatabase.getRecipeDao();
    recipeWithIngredients = recipeDao.getAllRecipeWithIngredients();

    favoritesViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
        .get(FavoritesViewModel.class);

    updateFavorites(recipeWithIngredients);
  }

  private void updateFavorites(List<RoomRecipeWithIngredients> favRecipe) {

    for (int i = 0; i < favRecipe.size(); i++) {
      favItemArrayList.add(new FavoriteRecipe(
          favRecipe.get(i).roomRecipe.getName(),
          favRecipe.get(i).roomRecipe.getImageUrl()
      ));
    }

    for (FavoriteRecipe i : favItemArrayList) {
      HashMap<String, String> favoriteItemHash = new HashMap<>();
      favoriteItemHash.put("favName", i.mFavoriteRecipe);
      favoriteItemHash.put("favImg", i.mFavoriteImage);
      favItemHashMap.add(favoriteItemHash);
    }

    SimpleAdapter simpleAdapter = new SimpleAdapter(this, favItemHashMap, R.layout.favorites_list_view,
        new String[]{"favImg", "favName"},
        new int[]{R.id.fav_list_img_view, R.id.fav_label_view}
    );

    ListView favListView = (ListView) findViewById(R.id.favorites_listView);
    favListView.setAdapter(simpleAdapter);


    favListView.setOnItemClickListener((parent, view, position, id) -> {
      Intent intent = new Intent(this, RecipeView.class);

      List<Ingredient> tempIngredientList = new ArrayList<>();

      for (int i = 0; i < favRecipe.get(position).roomIngredients.size(); i++) {
        Ingredient tempIngredient = new Ingredient(favRecipe.get(position).roomIngredients.get(i).getName(),
            favRecipe.get(position).roomIngredients.get(i).getQuantity(),
            favRecipe.get(position).roomIngredients.get(i).getMeasure(),
            favRecipe.get(position).roomIngredients.get(i).getText());
        tempIngredientList.add(tempIngredient);
      }

      Recipe recipeToRecipeView = new Recipe(
          favRecipe.get(position).roomRecipe.getName(),
          null,
          favRecipe.get(position).roomRecipe.getRecipeUrl(),
          tempIngredientList,
          null,
          favRecipe.get(position).roomRecipe.getPreparationTime());

      // intent content name recipe
      intent.putExtra("recipe", Parcels.wrap(recipeToRecipeView));
      startActivity(intent);
    });

    favListView.setOnItemLongClickListener((parent, view, position, id) -> {

      Recipe recipeToBeRemoved = new Recipe(
          null,
          null,
          favRecipe.get(position).roomRecipe.getRecipeUrl(),
          null,
          null,
          null);

      BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FavoritesView.this, R.style.BottomSheetDialogTheme);
      View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_popup_layout,
          (LinearLayout) findViewById(R.id.bottomSheetContainer));

      bottomSheetDialog.setContentView(bottomSheetView);
      bottomSheetDialog.show();

      bottomSheetView.findViewById(R.id.cancel_button).setOnClickListener(v1 -> {
        Toast.makeText(FavoritesView.this, "Canceled", Toast.LENGTH_LONG).show();
        bottomSheetDialog.dismiss();
      });

      bottomSheetView.findViewById(R.id.confirm_button).setOnClickListener(v1 -> {
        removeFromFav(recipeToBeRemoved);
        favItemHashMap.remove(position);
        simpleAdapter.notifyDataSetChanged();
        Toast.makeText(FavoritesView.this, "Removed from favorites", Toast.LENGTH_LONG).show();
        bottomSheetDialog.dismiss();
      });
      return true;
    });
  }

  public void removeFromFav(Recipe rec) {
    favoritesViewModel.removeRecipeFromFavourites(rec);
  }
}