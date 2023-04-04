package com.kh.product.product.svc;

import com.kh.product.product.dao.Product;
import com.kh.product.product.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC {


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
     * @param pid
     * @return
     */
    @Override
    public Optional<Product> findById(Long pid) {
        return productDAO.findById(pid);
    }

    /**
     * @param pid
     * @param product
     * @return
     */
    @Override
    public int update(Long pid, Product product) {
        return productDAO.update(pid, product);
    }

    /**
     * @param pid
     * @return
     */
    @Override
    public int delete(Long pid) {
        return productDAO.delete(pid);
    }

    /**
     * @param pids
     * @return
     */
    @Override
    public int deleteParts(List<Long> pids) {
        return productDAO.deleteParts(pids);
    }

    /**
     * @return
     */
    @Override
    public int deleteAll() {
        return productDAO.deleteAll();
    }

    /**
     * @return
     */
    @Override
    public List<Product> findAll() {
        return null;
    }

    /**
     * @param pid
     * @return
     */
    @Override
    public boolean isExist(Long pid) {
        return productDAO.isExist(pid);
    }

    /**
     * @return
     */
    @Override
    public int countOfRecord() {
        return 0;
    }
}
