package com.kh.app.web.form.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
//등록처리
public class UpdateForm {
    private Long productId;
    //수정할때
    @NotBlank
    private String pname;
    @NotNull
    private Long quantity;
    @NotNull
    private Long price;
}
