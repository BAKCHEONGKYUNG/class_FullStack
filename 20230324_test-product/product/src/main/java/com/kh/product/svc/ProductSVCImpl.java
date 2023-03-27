package com.kh.product.svc;

import com.kh.product.dao.Product;
import com.kh.product.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC{

    private final ProductDAO productDAO;
    /**
     * @param product
     * @return
     */
    @Override
    public Long save(Product product) {

        return productDAO.save(product);
    }

    /**
     *
     * @param pId
     * @return
     */
    @Override
    public Optional<Product> findById(Long pId){

        return productDAO.findById(pId);
    }

    /**
     * @param pId
     * @param product
     * @return
     */
    @Override
    public int update(Long pId, Product product) {
        return productDAO.update(pId, product);
    }

    /**
     *
     * @param pId
     * @return
     */
    @Override
    public int delete(Long pId) {
        return productDAO.delete(pId);
    }

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }


}
