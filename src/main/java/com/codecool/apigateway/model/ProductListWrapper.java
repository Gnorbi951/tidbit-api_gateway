package com.codecool.apigateway.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductListWrapper {
    private List<Product> list;
}
