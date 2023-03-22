package com.kh.myProduct.web.rest;

import lombok.Data;

@Data
//등록처리
public class UpdateRest {
    private Long productId;
    private String pname;
    private Long quantity;
    private Long price;
}
