package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ole.model.Suggestions;
import com.example.ole.viewmodel.SuggestionsViewModel;
import com.example.ole.viewmodel.factory.SuggestionsViewModelFactory;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuggestionsView extends AppCompatActivity {

    private SuggestionsViewModel suggestionsViewModel;

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
                new int[] {R.id.suggestion_text_view, R.id.suggestionImageView}
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
        findViewById(R.id.next_suggestions_button).setClickable(false);
        suggestionsViewModel.nextSuggestions();
    }
}