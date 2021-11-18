package com.example.ole.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ole.roomsitems.RoomRecipe;
import com.example.ole.roomsitems.RoomShoppingListItem;

import java.util.List;

@Dao
public interface ShoppingListDao {

  @Query("SELECT ingredient FROM shopping_list")
  List<String> getAllIngredients();

  @Insert
  long insertOne(RoomShoppingListItem shoppingListItem);

  @Insert
  long[] insertAll(RoomShoppingListItem... shoppingListItems);

  @Delete
  void delete(RoomShoppingListItem shoppingListItem);

}
