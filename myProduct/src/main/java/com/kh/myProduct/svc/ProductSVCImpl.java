package com.kh.myProduct.svc;

import com.kh.myProduct.dao.Product;
import com.kh.myProduct.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//DAO 단에서 지정한대로 방환을 해준다.
@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC {

    private final ProductDAO productDAO;
    @Override
    public Long save(Product product) {

        return productDAO.save(product);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productDAO.findById(productId);
    }

    @Override
    public int update(Long productId, Product product) {
        return productDAO.update(productId, product);
    }

    @Override
    public int delete(Long productId) {
        return productDAO.delete(productId);
    }

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    /**
     * 상품의 존재유무
     *
     * @param productId 상품아이디
     * @return
     */
    @Override
    public boolean isExist(Long productId) {
        return productDAO.isExist(productId);
    }
}
