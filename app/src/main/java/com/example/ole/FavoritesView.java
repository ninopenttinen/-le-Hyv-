package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.example.ole.components.CategoryItem;
import com.example.ole.components.FavoriteRecipe;
import com.example.ole.dao.RecipeDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.model.Recipe;
import com.example.ole.roomsitems.RoomRecipeWithIngredients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavoritesView extends AppCompatActivity {

    AppDatabase appDatabase;
    RecipeDao recipeDao;
    List<RoomRecipeWithIngredients> recipeWithIngredients;

    private final List<FavoriteRecipe> favItemArrayList = new ArrayList<FavoriteRecipe>();
    private final List<HashMap<String, String>> favItemHashMap = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        appDatabase = AppDatabase.getInstance(this);
        recipeDao  = appDatabase.getRecipeDao();
        recipeWithIngredients = recipeDao.getAllRecipeWithIngredients();

        String J=",";

        updateFavorites(recipeWithIngredients);
    }

    private void updateFavorites(List<RoomRecipeWithIngredients> favRecipe){

        for ( int i = 0; i < favRecipe.size(); i++){
            favItemArrayList.add( new FavoriteRecipe( favRecipe.get(i).roomRecipe.getName(), favRecipe.get(i).roomRecipe.getImageUrl().toString() ));
        }

        for (FavoriteRecipe i : favItemArrayList){
            HashMap<String, String> favoriteItemHash = new HashMap<>();
            favoriteItemHash.put("favName", i.mFavoriteRecipe);
            favoriteItemHash.put("favImg", i.mFavoriteImage);
            favItemHashMap.add( favoriteItemHash );
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, favItemHashMap, R.layout.favorites_list_view,
                new String[]{"favImg", "favName" },
                new int[]{R.id.fav_list_img_view, R.id.fav_label_view}
        );

        // Add adapter to gridView
        ListView favListView = ( ListView ) findViewById( R.id.favorites_listView );
        favListView.setAdapter(simpleAdapter);
    }


}