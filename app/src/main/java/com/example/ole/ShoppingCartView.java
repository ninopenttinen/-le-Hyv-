package com.example.ole;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ole.dao.ShoppingListDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.roomsitems.RoomShoppingListItem;
import com.example.ole.viewmodel.ShoppingListViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCartView extends AppCompatActivity {
  private final List<HashMap<String, String>> shoppingItemHashMapList = new ArrayList<>();
  private SimpleAdapter simpleAdapter;
  private ShoppingListDao shoppingListDao;
  private ShoppingListViewModel shoppingListViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cart);

    AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
    shoppingListDao = appDatabase.getShoppingListDao();
    shoppingListViewModel = new ViewModelProvider(
        this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
    ).get(ShoppingListViewModel.class);

    initializeListView();
  }

  private void initializeListView() {
    final String adapterIngredientName = "ingredientName";

    List<String> ingredientsList = shoppingListViewModel.getAllIngredients();

    for (String ingredientString : ingredientsList) {
      HashMap<String, String> ingredientItemHash = new HashMap<>();
      ingredientItemHash.put(adapterIngredientName, ingredientString);
      shoppingItemHashMapList.add(ingredientItemHash);
    }

    simpleAdapter = new SimpleAdapter(
        getApplicationContext(),
        shoppingItemHashMapList,
        R.layout.shopping_items_list,
        new String[]{adapterIngredientName},
        new int[]{R.id.shoppingItemName}
    ) {
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        Button itemDeleteButton = view.findViewById(R.id.shoppingItemDeleteButton);

        itemDeleteButton.setOnClickListener(arg0 -> {
          shoppingListViewModel.removeIngredientFromShoppingList(
              shoppingItemHashMapList.get(position).get(adapterIngredientName)
          );
          shoppingItemHashMapList.remove(position);
          simpleAdapter.notifyDataSetChanged();
        });

        return view;
      }
    };

    ListView shoppingItemsListView = findViewById(R.id.shoppingItemsList);
    shoppingItemsListView.setAdapter(simpleAdapter);
  }
}