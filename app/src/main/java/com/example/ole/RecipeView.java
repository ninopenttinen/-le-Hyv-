package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ole.model.Ingredient;
import com.example.ole.model.Recipe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class RecipeView extends AppCompatActivity {

    public static List<String> districtList = new ArrayList<String>();
    public String recipeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

        recipeUrl = recipe.getUrl();

        TextView recipeNameTextView = findViewById(R.id.recipeNameTextView);
        recipeNameTextView.setText(getString(R.string.recipe_name_value, recipe.getLabel()));

        ImageView recipeImageView = findViewById(R.id.recipeImageView);
        recipeImageView.setImageBitmap(recipe.getImage());

        TextView recipeTotalTime = findViewById(R.id.timeTextNumberView);
        recipeTotalTime.setText(recipe.getTotalTime());

        createListView(recipe.getIngredients());

    }

    private void createListView(List<Ingredient> ingredients) {

        ListView listView = new ListView(this);
        ArrayAdapter adapter;
        listView = findViewById(R.id.list_view);

        for(int i = 0; i < ingredients.size(); i++){
            districtList.add(ingredients.get(i).getText());
        }

        adapter = new ArrayAdapter(RecipeView.this,
                android.R.layout.simple_list_item_1,districtList);

        listView.setAdapter(adapter);

    }

    public void onClickUrl(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(recipeUrl));
        startActivity(i);
    }
}