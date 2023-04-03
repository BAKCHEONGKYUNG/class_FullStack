package com.kh.app.web.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //모든 멤버필스의 생성자를 만든다.
public class CodeDecode {

    private String code;        //코드
    private String decode;      //디코드(코드에 대한 설명)
}
