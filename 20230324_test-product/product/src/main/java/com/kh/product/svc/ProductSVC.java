package com.kh.product.svc;

import com.kh.product.dao.Product;

import java.util.List;
import java.util.Optional;

public interface ProductSVC {
    Long save(Product product);

    Optional<Product> findById(Long pId);

    int update(Long pId, Product product);

    int delete(Long pId);

    List<Product> findAll();
}
