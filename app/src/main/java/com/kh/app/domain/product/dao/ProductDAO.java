package com.kh.app.domain.product.dao;

import com.kh.app.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    //In, Out 정의
    /**
     * 등록
     * @param product 상품아이디
     * @return 상품
     */
    Long save(Product product);

    /**
     * 조회
     * @param productId 상품아이디
     * @return 상품
     */
    //Optional 은 컨테이너의 일종이며, 값이 있을 수도 있고, 없을 수도 있다.
    Optional<Product> findById(Long productId);

    /**
     * 수정(명확하게, productId, product)
     * @param productId 상품아이디
     * @param product 상품
     * @return 수정된 레코드 수
     */
    //(수정할 Id, 수정할 데이터(상품))
    // int 수정할 개수
    int update(Long productId, Product product);

    /**
     * 삭제(삭제건수 반환)
     * @param productId 상품아이디
     * @return 삭제된 레코드 수
     */
    //삭제완료시 반환1, 삭제할 것이 없다면 0
    int delete(Long productId);

    /**
     * 부분삭제
     * @param productIds
     * @return
     */
    int deleteParts(List<Long> productIds);

    /**
     * 전체 삭제
     * @return 삭제한 레코드 건수
     */
    int deleteAll();

    /**
     * 목록
     * @return 상품목록
     */
    List<Product> findAll();

    /**
     * 상품의 존재유무
     * @param productId 상품아이디
     * @return
     */
    boolean isExist(Long productId);

    /**
     * 등록된 상품건수
     * @return 레코드 건수
     */
    int countOfRecord();
}
