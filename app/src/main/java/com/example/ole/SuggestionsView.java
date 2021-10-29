package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.ole.components.RandomItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuggestionsView extends AppCompatActivity {

    private final List<RandomItem> randomItemArrayList = new ArrayList<RandomItem>();
    private final List<HashMap<String, String>> randomItemHashMap = new ArrayList<>();
    private SimpleAdapter simpleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);
        for (int i = 0; i < 2; i++) {
            String categoryName = "Category" + " " + (i);
            String categoryImg = "Image" + " " + (i);
            randomItemArrayList.add(new RandomItem(categoryName, categoryImg));
        }

        for (RandomItem i : randomItemArrayList) {
            HashMap<String, String> randomItemHash = new HashMap<>();
            randomItemHash.put("categoryName", i.mRndCategory);
            randomItemHash.put("categoryImg", i.mRndImage);
            randomItemHashMap.add(randomItemHash);
        }

        simpleAdapter = new SimpleAdapter(this, randomItemHashMap, R.layout.random_layout,
                new String[]{"categoryName", "categoryImg"},
                new int[]{R.id.randomImageView, R.id.randomTextView}
        );

        // Add adapter to gridView
        GridView randomGridView = (GridView) findViewById(R.id.randomGridView);
        randomGridView.setAdapter(simpleAdapter);
    }

    public void randomOnClick(View view) {
        Intent intent = new Intent(this, RecipeView.class);
        startActivity(intent);
    }
}