package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ole.dao.ShoppingListDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.roomsitems.RoomShoppingListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCartView extends AppCompatActivity {
  private final List<String> shoppingItemList = new ArrayList<>();
  private final List<HashMap<String, String>> shoppingItemHashMapList = new ArrayList<>();
  private SimpleAdapter simpleAdapter;
  private ShoppingListDao shoppingListDao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cart);

    AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
    shoppingListDao = appDatabase.getShoppingListDao();

    initializeListView();
  }

  private void initializeListView() {
    shoppingListDao.deleteAll();

    RoomShoppingListItem roomShoppingListItem = new RoomShoppingListItem();
    roomShoppingListItem.setIngredient("pizza");
    roomShoppingListItem.setAmount(123);

    shoppingListDao.insertAll(roomShoppingListItem);
    List<String> ingredientsList = shoppingListDao.getAllIngredients();

    for (String ingredientString : ingredientsList) {
      HashMap<String, String> ingredientItemHash = new HashMap<>();
      ingredientItemHash.put("ingredientName", ingredientString);
      shoppingItemHashMapList.add(ingredientItemHash);
    }

    simpleAdapter = new SimpleAdapter(
        getApplicationContext(),
        shoppingItemHashMapList,
        R.layout.shopping_items_list,
        new String[]{"ingredientName"},
        new int[]{R.id.shoppingItemName}
    );

    ListView shoppingItemsListView = (ListView) findViewById(R.id.shoppingItemsList);
    shoppingItemsListView.setAdapter(simpleAdapter);
  }
}