package com.kh.product.web.form;

import lombok.Data;

@Data
public class DetailForm {
    private Long pId;
    private String pname;
    private Long quantity;
    private Long price;
}
