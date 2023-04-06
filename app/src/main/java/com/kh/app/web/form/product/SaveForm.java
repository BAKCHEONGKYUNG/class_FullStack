package com.kh.app.web.form.product;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
//등록처리
public class SaveForm {
    //기본적인 검증 실행

    @NotBlank   //null, 빈문자열("")을 허용 안함.
    @Size(min=2, max=10)
    private String pname;
    @NotNull(message="공백불가")    //모든 타입에 대해 null허용 X
    @Positive   //양수
    @Max(1000)  //수량은 1000개를 넘을 수 없다.
    private Long quantity;
    @NotNull    //모든 타입에 대해 null허용 X
    @Positive   //양수
    @Min(1000)  //최소값
    private Long price;

    private MultipartFile attachFile;   //일반파일
    private List<MultipartFile> imageFiles;     //이미지 파일
}
