package com.example.ole.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.ole.model.FilterType;
import com.example.ole.roomsitems.RoomFilter;

import java.util.List;

@Dao
public interface FiltersDao {

    @Query("SELECT * FROM filters")
    List<RoomFilter> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomFilter filter);

    @Query("DELETE FROM filters WHERE name = :filterName AND type = :filterType")
    void delete(String filterName, FilterType filterType);

}
