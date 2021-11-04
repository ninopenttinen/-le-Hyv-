package com.example.ole.roomsitems;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SearchCriteria {

  @PrimaryKey(autoGenerate = true)
  private long id;

  private boolean noJuice;
  private boolean onlyVodka;
}
