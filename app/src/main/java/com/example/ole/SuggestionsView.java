package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ole.components.RandomItem;
import com.example.ole.model.Suggestions;
import com.example.ole.viewmodel.SuggestionsViewModel;
import com.example.ole.viewmodel.factory.SuggestionsViewModelFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuggestionsView extends AppCompatActivity {

    private SuggestionsViewModel suggestionsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        Bundle bundle = getIntent().getExtras();
        String category = bundle.getString("category");

        TextView categoryTextView = findViewById(R.id.categoryTextView);
        categoryTextView.setText(getString(R.string.selected_category_value, category));

        suggestionsViewModel = new ViewModelProvider(this, new SuggestionsViewModelFactory(this.getApplication(), category))
                .get(SuggestionsViewModel.class);

        suggestionsViewModel.getSuggestions().observe(this, suggestions -> displaySuggestions(suggestions));
    }

    private void displaySuggestions(Suggestions suggestions) {
        if (suggestions.isNoRecipes()) {
            return;
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                buildHashmapFromSuggestions(suggestions),
                R.layout.suggestion_card,
                new String[] {"label", "image"},
                new int[] {R.id.suggestionTextView, R.id.suggestionImageView}
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

        GridView suggestionGridView = findViewById(R.id.suggestionsGridView);
        suggestionGridView.setAdapter(adapter);
    }

    private List<HashMap<String, Object>> buildHashmapFromSuggestions(Suggestions suggestions) {
        HashMap<String, Object> firstSuggestion = new HashMap<>();
        firstSuggestion.put("label", suggestions.getFirstSuggestion().getLabel());
        firstSuggestion.put("image", suggestions.getFirstSuggestion().getImage());
        HashMap<String, Object> secondSuggestion = new HashMap<>();
        secondSuggestion.put("label", suggestions.getSecondSuggestion().getLabel());
        secondSuggestion.put("image", suggestions.getSecondSuggestion().getImage());

        List<HashMap<String, Object>> suggestionsHashMap = new ArrayList<>();
        suggestionsHashMap.add(firstSuggestion);
        suggestionsHashMap.add(secondSuggestion);
        return suggestionsHashMap;
    }

    public void getNextSuggestions(View view) {
        suggestionsViewModel.nextSuggestions();
    }

    public void openRecipe(View view) {

    }
}