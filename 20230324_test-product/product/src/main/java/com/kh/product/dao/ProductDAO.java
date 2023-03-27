package com.kh.product.dao;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Long save(Product product);

    Optional<Product> findById(Long pId);

    int update(Long pId, Product product);

    int delete(Long pId);

    List<Product> findAll();
}
