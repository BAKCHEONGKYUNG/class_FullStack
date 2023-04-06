package com.kh.app.domain.product.svc;

import com.kh.app.domain.common.dao.UploadFileDAO;
import com.kh.app.domain.entity.Product;
import com.kh.app.domain.entity.UploadFile;
import com.kh.app.domain.product.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//DAO 단에서 지정한대로 방환을 해준다.
@Service
@RequiredArgsConstructor
//한번에 처리하거나 처리하지 않기.(메소드 레벨에 사용가능하다.)
@Transactional
public class ProductSVCImpl implements ProductSVC {

    private final ProductDAO productDAO;
    private final UploadFileDAO uploadFileDAO;

    @Override
    public Long save(Product product) {
        return productDAO.save(product);
    }

    /**
     * @param product
     * @param uploadFiles
     * @return
     */

    @Override
    public Long save(Product product, List<UploadFile> uploadFiles) {
        Long productId = save(product);

        uploadFiles.stream().forEach(file -> file.setRid(productId));

        uploadFileDAO.addFiles(uploadFiles);

        return productId;
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

    /**
     * 부분삭제
     *
     * @param productIds
     * @return
     */
    @Override
    public int deleteParts(List<Long> productIds) {
        return productDAO.deleteParts(productIds);
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
