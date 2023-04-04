package com.kh.product.product.dao;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    //in, out정의

    //등록
    Long save(Product product);

    //조회
    Optional<Product> findById(Long pid);

    //수정
    int update(Long pid, Product product);

    //삭제
    int delete(Long pid);

    //부분삭제
    int deleteParts(List<Long> pids);

    //전체삭제
    int deleteAll();

    //목록
    List<Product> findAll();

    boolean isExist(Long pid);

    int countOfRecord();
}
