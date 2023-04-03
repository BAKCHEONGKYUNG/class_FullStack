package com.kh.app.domain.entity;

import lombok.Data;

@Data
public class Code {

    private String codeId;     //varchar2(11),       --코드
    private String decode;     //varchar2(30),       --코드명
    private String detail;     //clob,               --코드설명
    private String pcodeId;    //varchar2(11),       --상위코드
    private String useyn;     //char(1) default 'Y',            --사용여부 (사용:'Y',미사용:'N')
         //timestamp default systimestamp,         --생성일시
         //timestamp default systimestamp          --수정일시
}
