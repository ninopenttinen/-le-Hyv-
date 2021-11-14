package com.example.ole.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Filter {
    FilterType filterType;
    String filterName;
}
