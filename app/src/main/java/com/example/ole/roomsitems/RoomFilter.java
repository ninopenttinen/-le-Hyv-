package com.example.ole.roomsitems;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.ole.model.FilterType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "filters", primaryKeys = {"type", "name"})
@Data
@NoArgsConstructor
public class RoomFilter {

    @ColumnInfo(name = "type")
    @NonNull
    private FilterType type;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;
}
