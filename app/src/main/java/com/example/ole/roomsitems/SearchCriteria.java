package com.example.ole.roomsitems;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SearchCriteria {

  @PrimaryKey(autoGenerate = true)
  private long searchCriteriaId;

  private boolean noJuice;
  private boolean onlyVodka;
}
