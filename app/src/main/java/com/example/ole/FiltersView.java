package com.example.ole;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ole.adapter.ExcludedIngredientsAdapter;
import com.example.ole.model.Filter;
import com.example.ole.model.FilterType;
import com.example.ole.model.Recipe;
import com.example.ole.viewmodel.FiltersViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FiltersView extends AppCompatActivity implements ExcludedIngredientsAdapter.ItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    FiltersViewModel filtersViewModel;
    ExcludedIngredientsAdapter adapter;

    BottomNavigationView bottomNavigationView;

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

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(FiltersView.this);
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_button_settings);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String homeState = getIntent().getStringExtra("HomeState");
        String category = getIntent().getStringExtra("category");
        //String recipe = getIntent().getStringExtra("recipe");
        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

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
                intentHome.putExtra("recipe", Parcels.wrap(recipe));
                startActivity(intentHome);
                overridePendingTransition(0,0);
                break;

            case R.id.bottom_menu_button_favorites:
                Intent intentFavorites = new Intent(FiltersView.this, FavoritesView.class);
                intentFavorites.putExtra("HomeState",homeState);
                intentFavorites.putExtra("category", category);
                intentFavorites.putExtra("recipe", Parcels.wrap(recipe));
                startActivity(intentFavorites);
                overridePendingTransition(0,0);
                break;

            case R.id.bottom_menu_button_cart:
                Intent intentCart = new Intent(FiltersView.this, ShoppingCartView.class);
                intentCart.putExtra("HomeState",homeState);
                intentCart.putExtra("category", category);
                intentCart.putExtra("recipe", Parcels.wrap(recipe));
                startActivity(intentCart);
                overridePendingTransition(0,0);
                break;

            case R.id.bottom_menu_button_settings:
                return true;
        }
        return false;
    }

    @SuppressLint("NonConstantResourceId")
    public void showContents(View v) {
        LinearLayout contents = null;
        ImageView arrow = null;

        switch (v.getId()) {
            case R.id.meal_type_collapse:
                contents = findViewById(R.id.meal_type_filters);
                arrow = findViewById(R.id.meal_type_arrow);
                break;
            case R.id.dish_type_collapse:
                contents = findViewById(R.id.dish_type_filters);
                arrow = findViewById(R.id.dish_type_arrow);
                break;
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
            case R.id.meal_type_breakfast_filter:
                toggleFilter(findViewById(R.id.meal_type_breakfast_filter), new Filter(FilterType.MEAL_TYPE, "breakfast"));
                break;
            case R.id.meal_type_dinner_filter:
                toggleFilter(findViewById(R.id.meal_type_dinner_filter), new Filter(FilterType.MEAL_TYPE, "dinner"));
                break;
            case R.id.meal_type_lunch_filter:
                toggleFilter(findViewById(R.id.meal_type_lunch_filter), new Filter(FilterType.MEAL_TYPE, "lunch"));
                break;
            case R.id.meal_type_snack_filter:
                toggleFilter(findViewById(R.id.meal_type_snack_filter), new Filter(FilterType.MEAL_TYPE, "snack"));
                break;
            case R.id.meal_type_teatime_filter:
                toggleFilter(findViewById(R.id.meal_type_teatime_filter), new Filter(FilterType.MEAL_TYPE, "teatime"));
                break;

            case R.id.dish_type_biscuits_and_cookies_filter:
                toggleFilter(findViewById(R.id.dish_type_biscuits_and_cookies_filter), new Filter(FilterType.DISH_TYPE, "biscuits-and-cookies"));
                break;
            case R.id.dish_type_bread_filter:
                toggleFilter(findViewById(R.id.dish_type_bread_filter), new Filter(FilterType.DISH_TYPE, "bread"));
                break;
            case R.id.dish_type_cereals_filter:
                toggleFilter(findViewById(R.id.dish_type_cereals_filter), new Filter(FilterType.DISH_TYPE, "cereals"));
                break;
            case R.id.dish_type_condiments_and_sauces_filter:
                toggleFilter(findViewById(R.id.dish_type_condiments_and_sauces_filter), new Filter(FilterType.DISH_TYPE, "condiments-and-sauces"));
                break;
            case R.id.dish_type_desserts_filter:
                toggleFilter(findViewById(R.id.dish_type_desserts_filter), new Filter(FilterType.DISH_TYPE, "desserts"));
                break;
            case R.id.dish_type_drinks_filter:
                toggleFilter(findViewById(R.id.dish_type_drinks_filter), new Filter(FilterType.DISH_TYPE, "drinks"));
                break;
            case R.id.dish_type_main_course_filter:
                toggleFilter(findViewById(R.id.dish_type_main_course_filter), new Filter(FilterType.DISH_TYPE, "main-course"));
                break;
            case R.id.dish_type_pancake_filter:
                toggleFilter(findViewById(R.id.dish_type_pancake_filter), new Filter(FilterType.DISH_TYPE, "pancake"));
                break;
            case R.id.dish_type_preps_filter:
                toggleFilter(findViewById(R.id.dish_type_preps_filter), new Filter(FilterType.DISH_TYPE, "preps"));
                break;
            case R.id.dish_type_preserve_filter:
                toggleFilter(findViewById(R.id.dish_type_preserve_filter), new Filter(FilterType.DISH_TYPE, "preserve"));
                break;
            case R.id.dish_type_salad_filter:
                toggleFilter(findViewById(R.id.dish_type_salad_filter), new Filter(FilterType.DISH_TYPE, "salad"));
                break;
            case R.id.dish_type_sandwiches_filter:
                toggleFilter(findViewById(R.id.dish_type_sandwiches_filter), new Filter(FilterType.DISH_TYPE, "sandwiches"));
                break;
            case R.id.dish_type_side_dish_filter:
                toggleFilter(findViewById(R.id.dish_type_side_dish_filter), new Filter(FilterType.DISH_TYPE, "side-dish"));
                break;
            case R.id.dish_type_soup_filter:
                toggleFilter(findViewById(R.id.dish_type_soup_filter), new Filter(FilterType.DISH_TYPE, "soup"));
                break;
            case R.id.dish_type_starter_filter:
                toggleFilter(findViewById(R.id.dish_type_starter_filter), new Filter(FilterType.DISH_TYPE, "starter"));
                break;
            case R.id.dish_type_sweets_filter:
                toggleFilter(findViewById(R.id.dish_type_sweets_filter), new Filter(FilterType.DISH_TYPE, "sweets"));
                break;

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

            case R.id.health_alcohol_free_filter:
                toggleFilter(findViewById(R.id.health_alcohol_free_filter), new Filter(FilterType.HEALTH, "alcohol-free"));
                break;
            case R.id.health_dairy_free_filter:
                toggleFilter(findViewById(R.id.health_dairy_free_filter), new Filter(FilterType.HEALTH, "dairy-free"));
                break;
            case R.id.health_egg_free_filter:
                toggleFilter(findViewById(R.id.health_egg_free_filter), new Filter(FilterType.HEALTH, "egg-free"));
                break;
            case R.id.health_fish_free_filter:
                toggleFilter(findViewById(R.id.health_fish_free_filter), new Filter(FilterType.HEALTH, "fish-free"));
                break;
            case R.id.health_fodmap_free_filter:
                toggleFilter(findViewById(R.id.health_fodmap_free_filter), new Filter(FilterType.HEALTH, "fodmap-free"));
                break;
            case R.id.health_gluten_free_filter:
                toggleFilter(findViewById(R.id.health_gluten_free_filter), new Filter(FilterType.HEALTH, "gluten-free"));
                break;
            case R.id.health_immuno_supportive_filter:
                toggleFilter(findViewById(R.id.health_immuno_supportive_filter), new Filter(FilterType.HEALTH, "immuno-supportive"));
                break;
            case R.id.health_keto_friendly_filter:
                toggleFilter(findViewById(R.id.health_keto_friendly_filter), new Filter(FilterType.HEALTH, "keto-friendly"));
                break;
            case R.id.health_kidney_friendly_filter:
                toggleFilter(findViewById(R.id.health_kidney_friendly_filter), new Filter(FilterType.HEALTH, "kidney-friendly"));
                break;
            case R.id.health_low_fat_abs_filter:
                toggleFilter(findViewById(R.id.health_low_fat_abs_filter), new Filter(FilterType.HEALTH, "low-fat-abs"));
                break;
            case R.id.health_low_potassium_filter:
                toggleFilter(findViewById(R.id.health_low_potassium_filter), new Filter(FilterType.HEALTH, "low-potassium"));
                break;
            case R.id.health_low_sugar_filter:
                toggleFilter(findViewById(R.id.health_low_sugar_filter), new Filter(FilterType.HEALTH, "low-sugar"));
                break;
            case R.id.health_paleo_filter:
                toggleFilter(findViewById(R.id.health_paleo_filter), new Filter(FilterType.HEALTH, "paleo"));
                break;
            case R.id.health_peanut_free_filter:
                toggleFilter(findViewById(R.id.health_peanut_free_filter), new Filter(FilterType.HEALTH, "peanut-free"));
                break;
            case R.id.health_pescatarian_filter:
                toggleFilter(findViewById(R.id.health_pescatarian_filter), new Filter(FilterType.HEALTH, "pescatarian"));
                break;
            case R.id.health_pork_free_filter:
                toggleFilter(findViewById(R.id.health_pork_free_filter), new Filter(FilterType.HEALTH, "pork-free"));
                break;
            case R.id.health_red_meat_free_filter:
                toggleFilter(findViewById(R.id.health_red_meat_free_filter), new Filter(FilterType.HEALTH, "red-meat-free"));
                break;
            case R.id.health_soy_free_filter:
                toggleFilter(findViewById(R.id.health_soy_free_filter), new Filter(FilterType.HEALTH, "soy-free"));
                break;
            case R.id.health_vegan_filter:
                toggleFilter(findViewById(R.id.health_vegan_filter), new Filter(FilterType.HEALTH, "vegan"));
                break;
            case R.id.health_vegetarian_filter:
                toggleFilter(findViewById(R.id.health_vegetarian_filter), new Filter(FilterType.HEALTH, "vegetarian"));
                break;
            case R.id.health_wheat_free_filter:
                toggleFilter(findViewById(R.id.health_wheat_free_filter), new Filter(FilterType.HEALTH, "wheat-free"));
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
        switches.put(new Filter(FilterType.MEAL_TYPE, "breakfast"), findViewById(R.id.meal_type_breakfast_filter));
        switches.put(new Filter(FilterType.MEAL_TYPE, "dinner"), findViewById(R.id.meal_type_dinner_filter));
        switches.put(new Filter(FilterType.MEAL_TYPE, "lunch"), findViewById(R.id.meal_type_lunch_filter));
        switches.put(new Filter(FilterType.MEAL_TYPE, "snack"), findViewById(R.id.meal_type_snack_filter));
        switches.put(new Filter(FilterType.MEAL_TYPE, "teatime"), findViewById(R.id.meal_type_teatime_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "biscuits-and-cookies"), findViewById(R.id.dish_type_biscuits_and_cookies_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "bread"), findViewById(R.id.dish_type_bread_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "cereals"), findViewById(R.id.dish_type_cereals_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "condiments-and-sauces"), findViewById(R.id.dish_type_condiments_and_sauces_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "desserts"), findViewById(R.id.dish_type_desserts_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "drinks"), findViewById(R.id.dish_type_drinks_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "main-course"), findViewById(R.id.dish_type_main_course_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "pancake"), findViewById(R.id.dish_type_pancake_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "preps"), findViewById(R.id.dish_type_preps_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "preserve"), findViewById(R.id.dish_type_preserve_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "salad"), findViewById(R.id.dish_type_salad_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "sandwiches"), findViewById(R.id.dish_type_sandwiches_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "side-dish"), findViewById(R.id.dish_type_side_dish_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "soup"), findViewById(R.id.dish_type_soup_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "starter"), findViewById(R.id.dish_type_starter_filter));
        switches.put(new Filter(FilterType.DISH_TYPE, "sweets"), findViewById(R.id.dish_type_sweets_filter));
        switches.put(new Filter(FilterType.DIET, "balanced"), findViewById(R.id.diet_balanced_filter));
        switches.put(new Filter(FilterType.DIET, "high-fiber"), findViewById(R.id.diet_high_fiber_filter));
        switches.put(new Filter(FilterType.DIET, "high-protein"), findViewById(R.id.diet_high_protein_filter));
        switches.put(new Filter(FilterType.DIET, "low-carb"), findViewById(R.id.diet_low_carb_filter));
        switches.put(new Filter(FilterType.DIET, "low-fat"), findViewById(R.id.diet_low_fat_filter));
        switches.put(new Filter(FilterType.DIET, "low-sodium"), findViewById(R.id.diet_low_sodium_filter));
        switches.put(new Filter(FilterType.HEALTH, "alcohol-free"), findViewById(R.id.health_alcohol_free_filter));
        switches.put(new Filter(FilterType.HEALTH, "dairy-free"), findViewById(R.id.health_dairy_free_filter));
        switches.put(new Filter(FilterType.HEALTH, "egg-free"), findViewById(R.id.health_egg_free_filter));
        switches.put(new Filter(FilterType.HEALTH, "fish-free"), findViewById(R.id.health_fish_free_filter));
        switches.put(new Filter(FilterType.HEALTH, "fodmap-free"), findViewById(R.id.health_fodmap_free_filter));
        switches.put(new Filter(FilterType.HEALTH, "gluten-free"), findViewById(R.id.health_gluten_free_filter));
        switches.put(new Filter(FilterType.HEALTH, "immuno-supportive"), findViewById(R.id.health_immuno_supportive_filter));
        switches.put(new Filter(FilterType.HEALTH, "keto-friendly"), findViewById(R.id.health_keto_friendly_filter));
        switches.put(new Filter(FilterType.HEALTH, "kidney-friendly"), findViewById(R.id.health_kidney_friendly_filter));
        switches.put(new Filter(FilterType.HEALTH, "low-fat-abs"), findViewById(R.id.health_low_fat_abs_filter));
        switches.put(new Filter(FilterType.HEALTH, "low-potassium"), findViewById(R.id.health_low_potassium_filter));
        switches.put(new Filter(FilterType.HEALTH, "low-sugar"), findViewById(R.id.health_low_sugar_filter));
        switches.put(new Filter(FilterType.HEALTH, "paleo"), findViewById(R.id.health_paleo_filter));
        switches.put(new Filter(FilterType.HEALTH, "peanut-free"), findViewById(R.id.health_peanut_free_filter));
        switches.put(new Filter(FilterType.HEALTH, "pescatarian"), findViewById(R.id.health_pescatarian_filter));
        switches.put(new Filter(FilterType.HEALTH, "pork-free"), findViewById(R.id.health_pork_free_filter));
        switches.put(new Filter(FilterType.HEALTH, "red-meat-free"), findViewById(R.id.health_red_meat_free_filter));
        switches.put(new Filter(FilterType.HEALTH, "soy-free"), findViewById(R.id.health_soy_free_filter));
        switches.put(new Filter(FilterType.HEALTH, "vegan"), findViewById(R.id.health_vegan_filter));
        switches.put(new Filter(FilterType.HEALTH, "vegetarian"), findViewById(R.id.health_vegetarian_filter));
        switches.put(new Filter(FilterType.HEALTH, "wheat-free"), findViewById(R.id.health_wheat_free_filter));


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
        filtersViewModel.removeFilter( new Filter(FilterType.EXCLUDED, adapter.getItem(position)) );
    }

}