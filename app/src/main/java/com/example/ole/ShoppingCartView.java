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
        switch (item.getItemId()) {
            case R.id.bottom_menu_button_home:
                Intent intentHome = new Intent(ShoppingCartView.this, CategoryView.class);
                startActivity(intentHome);
                overridePendingTransition(0,0);
                break;

            case R.id.bottom_menu_button_favorites:
                Intent intentFavorites = new Intent(ShoppingCartView.this, FavoritesView.class);
                startActivity(intentFavorites);
                overridePendingTransition(0,0);
                break;

            case R.id.bottom_menu_button_cart:
                return true;

            case R.id.bottom_menu_button_settings:
                Intent intentSettings = new Intent(ShoppingCartView.this, FiltersView.class);
                startActivity(intentSettings);
                overridePendingTransition(0,0);
                break;
        }
        return false;
    }
}