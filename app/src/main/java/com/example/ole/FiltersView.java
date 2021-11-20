package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ole.adapter.ExcludedIngredientsAdapter;
import com.example.ole.model.Filter;
import com.example.ole.model.FilterType;
import com.example.ole.viewmodel.FiltersViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FiltersView extends AppCompatActivity implements ExcludedIngredientsAdapter.ItemClickListener {

    FiltersViewModel filtersViewModel;
    ExcludedIngredientsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        filtersViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(this.getApplication()))
                .get(FiltersViewModel.class);

        filtersViewModel.getFilters().observe(this, filters -> {
            setRecyclerView(getExcludedIngredients(filters));
            setSwitches(filters);
        });

        setEditTextListeners(findViewById(R.id.ingredient_filter_edit_text));
    }

    @SuppressLint("NonConstantResourceId")
    public void showContents(View v) {
        LinearLayout contents = null;
        ImageView arrow = null;

        switch (v.getId()) {
            case R.id.diet_collapse:
                contents = findViewById(R.id.diet_filters);
                arrow = findViewById(R.id.diet_arrow);
                break;
            case R.id.health_collapse:
                contents = findViewById(R.id.health_filters);
                arrow = findViewById(R.id.health_arrow);
                break;
        }

        if (contents != null && arrow != null) {
            if (contents.isShown()) {
                //Animations.slide_up(this, contents);
                contents.setVisibility(View.GONE);
                arrow.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow_drop_down_24, null)
                );
            } else {
                //Animations.slide_down(this, contents);
                contents.setVisibility(View.VISIBLE);
                arrow.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow_drop_up_24, null)
                );
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void handleToggle(View v) {
        switch (v.getId()) {
            case R.id.diet_balanced_filter:
                toggleFilter(findViewById(R.id.diet_balanced_filter), new Filter(FilterType.DIET, "balanced"));
                break;
            case R.id.diet_high_fiber_filter:
                toggleFilter(findViewById(R.id.diet_high_fiber_filter), new Filter(FilterType.DIET, "high-fiber"));
                break;
            case R.id.diet_high_protein_filter:
                toggleFilter(findViewById(R.id.diet_high_protein_filter), new Filter(FilterType.DIET, "high-protein"));
                break;
            case R.id.diet_low_carb_filter:
                toggleFilter(findViewById(R.id.diet_low_carb_filter), new Filter(FilterType.DIET, "low-carb"));
                break;
            case R.id.diet_low_fat_filter:
                toggleFilter(findViewById(R.id.diet_low_fat_filter), new Filter(FilterType.DIET, "low-fat"));
                break;
            case R.id.diet_low_sodium_filter:
                toggleFilter(findViewById(R.id.diet_low_sodium_filter), new Filter(FilterType.DIET, "low-sodium"));
                break;
        }
    }

    private void toggleFilter(SwitchCompat switchCompat, Filter filter) {
        if (switchCompat.isChecked()) {
            filtersViewModel.addFilter(filter);
        } else {
            filtersViewModel.removeFilter(filter);
        }
    }

    private void setRecyclerView(List<String> excludedIngredients) {
        RecyclerView recyclerView = findViewById(R.id.excluded_ingredients_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ExcludedIngredientsAdapter(excludedIngredients);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void setSwitches(List<Filter> filters) {
        HashMap<Filter, SwitchCompat> switches = new HashMap<>();
        switches.put(new Filter(FilterType.DIET, "balanced"), findViewById(R.id.diet_balanced_filter));
        switches.put(new Filter(FilterType.DIET, "high-fiber"), findViewById(R.id.diet_high_fiber_filter));
        switches.put(new Filter(FilterType.DIET, "high-protein"), findViewById(R.id.diet_high_protein_filter));
        switches.put(new Filter(FilterType.DIET, "low-carb"), findViewById(R.id.diet_low_carb_filter));
        switches.put(new Filter(FilterType.DIET, "low-fat"), findViewById(R.id.diet_low_fat_filter));
        switches.put(new Filter(FilterType.DIET, "low-sodium"), findViewById(R.id.diet_low_sodium_filter));

        for (Map.Entry<Filter, SwitchCompat> entry : switches.entrySet()) {
            entry.getValue().setChecked(
                    filters.stream()
                            .anyMatch(f ->
                                    f.getFilterName().equals(entry.getKey().getFilterName()) &&
                                    f.getFilterType().equals(entry.getKey().getFilterType()))
            );
        }
    }

    private List<String> getExcludedIngredients(List<Filter> filters) {
        return filters.stream()
                .filter(f -> f.getFilterType().equals(FilterType.EXCLUDED))
                .map(Filter::getFilterName)
                .collect(Collectors.toList());
    }

    private void setEditTextListeners(EditText ingredientFilterEditText) {
        ingredientFilterEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard(ingredientFilterEditText);
                return true;
            }
            return false;
        });
        ingredientFilterEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(ingredientFilterEditText);
                filtersViewModel.addFilter(new Filter(FilterType.EXCLUDED, ingredientFilterEditText.getText().toString()));
                ingredientFilterEditText.setText("");
            }
        });
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void onItemClick(View v, int position) {
        filtersViewModel.removeExclusionFilter(adapter.getItem(position));
    }

}