package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.ole.components.CategoryItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryView extends AppCompatActivity {

    private String jsonResponse = "Test";
    private final List<CategoryItem> categoryItemArrayList = new ArrayList< CategoryItem >();
    private List<HashMap<String, String>> categoryItemHashMap = new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseJsonAndUpdate(jsonResponse);
    }

    private void parseJsonAndUpdate(String jsonResponse){
        // try catch
        // Fill list with items

        for ( int i = 0; i < 15; i++){
            String categoryName = "Category" + " " + (i);
            String categoryImg = "Image" + " " + (i);
            categoryItemArrayList.add( new CategoryItem( categoryName, categoryImg ));
        }

        for (CategoryItem i : categoryItemArrayList){
            HashMap<String, String> categoryItemHash = new HashMap<>();
            categoryItemHash.put("categoryName", i.mCategoryName);
            categoryItemHash.put("categoryImg", i.mCategoryImage);
            categoryItemHashMap.add( categoryItemHash );
        }

        simpleAdapter = new SimpleAdapter( this, categoryItemHashMap, R.layout.category_grid_layout,
                new String[] { "categoryName", "categoryImg" },
                new int[] { R.id.categoryImageView, R.id.categoryTextView }
                );

        // Add adapter to gridView
        GridView categoryGridView = ( GridView ) findViewById( R.id.gridView );
        categoryGridView.setAdapter( simpleAdapter );
    }

    public void categoryOnClick(View view){
        Intent intent = new Intent(this, SuggestionsView.class);
        startActivity(intent);
    }

    public void settingsOnClick(View view){
        Intent intent = new Intent(this, SettingsView.class);
        startActivity(intent);
    }

    public void cartOnClick(View view){
        Intent intent = new Intent(this, ShoppingCartView.class);
        startActivity(intent);
    }

    public void favoritesOnClick(View view){
        Intent intent = new Intent(this, FavoritesView.class);
        startActivity(intent);
    }

}