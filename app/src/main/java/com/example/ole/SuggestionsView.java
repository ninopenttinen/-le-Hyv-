package com.example.ole;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ole.model.Suggestions;
import com.example.ole.viewmodel.SuggestionsViewModel;
import com.example.ole.viewmodel.factory.SuggestionsViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuggestionsView extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

  private SuggestionsViewModel suggestionsViewModel;

  BottomNavigationView bottomNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_suggestions);
    Bundle bundle = getIntent().getExtras();
    String category = bundle.getString("category");

    TextView categoryTextView = findViewById(R.id.category_text_view);
    categoryTextView.setText(getString(R.string.activity_suggestions_title, category));

    suggestionsViewModel = new ViewModelProvider(this, new SuggestionsViewModelFactory(this.getApplication(), category))
        .get(SuggestionsViewModel.class);

    suggestionsViewModel.getSuggestions().observe(this, suggestions -> {
      displaySuggestions(suggestions);
      findViewById(R.id.next_suggestions_button).setClickable(!suggestions.isNoRecipes());
    });

    bottomNavigationView = findViewById(R.id.bottomNavigationView);
    bottomNavigationView.setOnNavigationItemSelectedListener(SuggestionsView.this);
    bottomNavigationView.setSelectedItemId(R.id.bottom_menu_button_home);
  }

  @Override
  public void onBackPressed() {
    // TODO Auto-generated method stub
    //super.onBackPressed();
    Intent backIntent = new Intent(SuggestionsView.this, CategoryView.class);
    startActivity(backIntent);
    overridePendingTransition(0,0);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    String category = getIntent().getStringExtra("category");
    switch (item.getItemId()) {
      case R.id.bottom_menu_button_home:
        return true;

      case R.id.bottom_menu_button_favorites:
        Intent intentFavorites = new Intent(SuggestionsView.this, FavoritesView.class);
        intentFavorites.putExtra("HomeState", "suggestionsView");
        intentFavorites.putExtra("category",category);
        startActivity(intentFavorites);
        overridePendingTransition(0,0);
        break;

      case R.id.bottom_menu_button_cart:
        Intent intentCart = new Intent(SuggestionsView.this, ShoppingCartView.class);
        intentCart.putExtra("HomeState", "suggestionsView");
        intentCart.putExtra("category",category);
        startActivity(intentCart);
        overridePendingTransition(0,0);
        break;

      case R.id.bottom_menu_button_settings:
        Intent intentSettings = new Intent(SuggestionsView.this, FiltersView.class);
        intentSettings.putExtra("HomeState", "suggestionsView");
        intentSettings.putExtra("category",category);
        startActivity(intentSettings);
        overridePendingTransition(0,0);
        break;
    }
    return false;
  }

  private void displaySuggestions(Suggestions suggestions) {
    if (suggestions.isNoRecipes()) {
      return;
    }

    SimpleAdapter adapter = new SimpleAdapter(
        this,
        buildHashmapFromSuggestions(suggestions),
        R.layout.suggestion_card,
        new String[]{"label", "image"},
        new int[]{R.id.suggestion_text_view, R.id.suggestionImageView}
    );

    adapter.setViewBinder((view, data, textRepresentation) -> {
      if ((view instanceof ImageView) & (data instanceof Bitmap)) {
        ImageView iv = (ImageView) view;
        Bitmap bm = (Bitmap) data;
        iv.setImageBitmap(bm);
        return true;
      }
      return false;
    });

    GridView suggestionsGridView = findViewById(R.id.suggestions_grid_view);
    suggestionsGridView.setAdapter(adapter);

    suggestionsGridView.setOnItemClickListener((parent, view, position, id) -> {
      Intent intent = new Intent(this, RecipeView.class);
      if (position == 0) {
        intent.putExtra("recipe", Parcels.wrap(suggestions.getFirstSuggestion()));
      }
      if (position == 1) {
        intent.putExtra("recipe", Parcels.wrap(suggestions.getSecondSuggestion()));
      }
      startActivity(intent);
    });
  }

  private List<HashMap<String, Object>> buildHashmapFromSuggestions(Suggestions suggestions) {
    List<HashMap<String, Object>> suggestionsHashMap = new ArrayList<>();

    HashMap<String, Object> firstSuggestion = new HashMap<>();
    firstSuggestion.put("label", suggestions.getFirstSuggestion().getLabel());
    firstSuggestion.put("image", suggestions.getFirstSuggestion().getImage());
    suggestionsHashMap.add(firstSuggestion);

    if (suggestions.getSecondSuggestion() != null) {
      HashMap<String, Object> secondSuggestion = new HashMap<>();
      secondSuggestion.put("label", suggestions.getSecondSuggestion().getLabel());
      secondSuggestion.put("image", suggestions.getSecondSuggestion().getImage());
      suggestionsHashMap.add(secondSuggestion);
    }
    return suggestionsHashMap;
  }

  public void getNextSuggestions(View view) {
    findViewById(R.id.next_suggestions_button).setClickable(false);
    suggestionsViewModel.nextSuggestions();
  }
}