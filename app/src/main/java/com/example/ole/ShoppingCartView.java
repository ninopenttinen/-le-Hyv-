package com.example.ole;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShoppingCartView extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

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
}