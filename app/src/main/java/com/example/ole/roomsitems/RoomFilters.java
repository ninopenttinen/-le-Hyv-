package com.example.ole.roomsitems;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "filters")
@Data
@NoArgsConstructor
public class RoomFilters {

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "name")
    private String name;
}
