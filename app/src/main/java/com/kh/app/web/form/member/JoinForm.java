package com.kh.app.web.form.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

//저장하고 읽어오기 get, set
@Data
public class JoinForm {

    //검증 어노테이션
    //오류 검증 global
    //이 올 수 있다.

    @Email
    private String email;         //EMAIL	VARCHAR2(50 BYTE)
    @NotBlank
    @Size(min=4, max=12)
    private String passwd;        //PASSWD	VARCHAR2(12 BYTE)
    @NotBlank   //필수
    @Size(min=4, max=12)
    //비밀번호 확인, 서버에서 확인
    private String passwdchk;

    @Size(max = 10)
    private String nickname;      //NICKNAME	VARCHAR2(30 BYTE)
    private String gender;        //GENDER	VARCHAR2(6 BYTE)
    private List<String> hobby;         //HOBBY	VARCHAR2(300 BYTE)
    private String region;         //REGION	VARCHAR2(30 BYTE)
}
