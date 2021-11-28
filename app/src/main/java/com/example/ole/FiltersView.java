package com.example.ole;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FiltersView extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    //private ArrayList<String> selectedItems = new ArrayList<String>();
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(FiltersView.this);
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_button_settings);


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
                    intentHome = new Intent(FiltersView.this, CategoryView.class);
                }
                else if (homeState.equals("suggestionsView")){
                    intentHome = new Intent(FiltersView.this, SuggestionsView.class);
                }
                else if (homeState.equals("recipeView")){
                    intentHome = new Intent(FiltersView.this, RecipeView.class);
                }
                else{
                    return false;
                }
                intentHome.putExtra("category", category);
                intentHome.putExtra("recipe",recipe);
                startActivity(intentHome);
                overridePendingTransition(0,0);
                break;

            case R.id.bottom_menu_button_favorites:
                Intent intentFavorites = new Intent(FiltersView.this, FavoritesView.class);
                intentFavorites.putExtra("HomeState",homeState);
                intentFavorites.putExtra("category", category);
                intentFavorites.putExtra("recipe",recipe);
                startActivity(intentFavorites);
                overridePendingTransition(0,0);
                break;

            case R.id.bottom_menu_button_cart:
                Intent intentCart = new Intent(FiltersView.this, ShoppingCartView.class);
                intentCart.putExtra("HomeState",homeState);
                intentCart.putExtra("category", category);
                intentCart.putExtra("recipe",recipe);
                startActivity(intentCart);
                overridePendingTransition(0,0);
                break;

            case R.id.bottom_menu_button_settings:
                return true;
        }
        return false;
    }

    /*public Dialog dialogOnClick(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.pick_toppings)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(R.array.toppings, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    selectedItems.add(which);
                                } else if (selectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    selectedItems.remove(which);
                                }
                            }
                        })
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog
                   ...
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                   ...
                    }
                });

        return builder.create();
    }

    private Context getActivity() {
    }*/

    public void dialogOnClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Do you want to logout?");
        // alert.setMessage("Message");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Your action here
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        alert.show();
    }

}