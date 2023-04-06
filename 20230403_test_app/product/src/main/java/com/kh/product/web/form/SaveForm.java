package com.kh.product.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SaveForm {

    @NotBlank
    private String pname;       //    pname       varchar(30),

    @NotNull(message = "공백불가")
    @Positive
    private Long quantity;      //    quantity    number(10),
    @NotNull
    @Positive
    private Long price;         //    price       number(10)
}
