package com.kh.myProduct.web.form;

import lombok.Data;

@Data
//등록처리
public class UpdateForm {
    private Long productId;
    private String pname;
    private Long quantity;
    private Long price;
}
