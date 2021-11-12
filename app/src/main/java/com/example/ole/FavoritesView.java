package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Layer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.example.ole.components.CategoryItem;
import com.example.ole.components.FavoriteRecipe;
import com.example.ole.dao.RecipeDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.model.Recipe;
import com.example.ole.roomsitems.RoomRecipe;
import com.example.ole.roomsitems.RoomRecipeWithIngredients;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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

        updateFavorites(recipeWithIngredients);


        ImageButton imageButton = findViewById(R.id.fav_actions_button);
         /*  imageButton.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FavoritesView.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_popup_layout,
                    (LinearLayout)findViewById(R.id.bottomSheetContainer));
            *//*bottomSheetView.findViewById(R.id.)*//*
        });
*/
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

    public void favClick(View v){

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FavoritesView.this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_popup_layout,
                (LinearLayout)findViewById(R.id.bottomSheetContainer));

        bottomSheetView.findViewById(R.id.cancel_button).setOnClickListener(v1 -> {
            Toast.makeText(FavoritesView.this, "Canceled", Toast.LENGTH_LONG).show();
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.confirm_button).setOnClickListener(v1 -> {
            Toast.makeText(FavoritesView.this, "Recipe removed", Toast.LENGTH_LONG).show();

            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
}