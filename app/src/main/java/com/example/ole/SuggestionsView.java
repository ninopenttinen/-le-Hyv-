package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.ole.components.RandomItem;
import com.example.ole.model.Recipe;
import com.example.ole.viewmodel.SuggestionsViewModel;
import com.example.ole.viewmodel.factory.SuggestionsViewModelFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuggestionsView extends AppCompatActivity {

    private SuggestionsViewModel suggestionsViewModel;
    private final List<RandomItem> randomItemArrayList = new ArrayList< RandomItem >();
    private final List<HashMap<String, String>> randomItemHashMap = new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String category = bundle.getString("category");

        setContentView(R.layout.activity_second);

        suggestionsViewModel = new ViewModelProvider(this, new SuggestionsViewModelFactory(this.getApplication(), category))
                .get(SuggestionsViewModel.class);

        suggestionsViewModel.getRecipes().observe(this, recipes -> {
                    parseDataAndUpdate(recipes);

                    // Add adapter to gridView
                    GridView randomGridView = (GridView) findViewById(R.id.randomGridView);
                    randomGridView.setAdapter(simpleAdapter);
                }
                );

                // branch test
    }

    private void parseDataAndUpdate(List<Recipe> recipes) {
        if (recipes.size() == 0) {
            return;
        }

        randomItemArrayList.add( new RandomItem( recipes.get(0).getLabel(), recipes.get(0).getImage() ));
        randomItemArrayList.add( new RandomItem( recipes.get(1).getLabel(), recipes.get(1).getImage() ));

        for (RandomItem i : randomItemArrayList){
            HashMap<String, String> randomItemHash = new HashMap<>();
            randomItemHash.put("categoryName", i.mRndCategory);
            randomItemHash.put("categoryImg", i.mRndImage);
            randomItemHashMap.add( randomItemHash );
        }

        simpleAdapter = new SimpleAdapter( this, randomItemHashMap, R.layout.random_layout,
                new String[] { "categoryName", "categoryImg" },
                new int[] { R.id.randomImageView, R.id.randomTextView }
        );
    }

    public void randomOnClick(View view){
        Intent intent = new Intent(this, RecipeView.class);
        startActivity(intent);
    }
}