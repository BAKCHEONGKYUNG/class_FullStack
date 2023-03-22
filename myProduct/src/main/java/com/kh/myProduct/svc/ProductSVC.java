package com.kh.myProduct.svc;

import com.kh.myProduct.dao.Product;

import java.util.List;
import java.util.Optional;

//DAO단과 동일하게
public interface ProductSVC {
    //등록
    Long save(Product product);
    //조회
    Optional<Product> findById(Long productId);
    //수정(명확하게, productId, product)
    int update(Long productId, Product product);
    //삭제(삭제건수 반환)
    int delete(Long productId);
    //전체목록(상품을 담을 수 있는 컬렉션이 필요)
    List<Product> findAll();

    /**
     * 상품의 존재유무
     * @param productId 상품아이디
     * @return
     */
    boolean isExist(Long productId);

}
