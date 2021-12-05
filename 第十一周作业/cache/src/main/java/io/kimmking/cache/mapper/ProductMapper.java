package io.kimmking.cache.mapper;

import io.kimmking.cache.entity.Product;

import java.util.List;

public interface ProductMapper {
    Product reduce(int id);
    List<Product> all();
}
