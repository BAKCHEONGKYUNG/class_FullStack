package com.kh.myProduct.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   //@getter,@setter,@EqualsA~~~, @toString,@hashCode,@RequiredArgsConstructor
@AllArgsConstructor //모든 멤버필드를 매개변수로하는 생성자 생성
@NoArgsConstructor  //디폴트 생성자
public class Product { //Product 객체
    //멤버필드 초기값
    private Long productId;     // PRODUCT_ID	NUMBER(10,0)
    private String pname;       //PNAME	VARCHAR2(30 BYTE)
    private Long quantity;      //QUANTITY	NUMBER(10,0)
    private Long price;         //PRICE	NUMBER(10,0)
}

