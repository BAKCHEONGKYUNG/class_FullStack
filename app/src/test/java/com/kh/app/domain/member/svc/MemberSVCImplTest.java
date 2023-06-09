package com.kh.app.domain.member.svc;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberSVCImplTest {

    //MemberSVCImpl.java에서 해당하는 메소드 ctrl+shift+t 로 생성

    @Autowired
    MemberSVC memberSVC;

    @Test
    @DisplayName("회원 아이디 존재유무확인(중복확인)")
    void isExist() {
        boolean exist = memberSVC.isExist("test@kh.com");
        Assertions.assertThat(exist).isTrue();  //있는거, .isFalse() 없는것

        exist = memberSVC.isExist("test@kh.com111");
        Assertions.assertThat(exist).isFalse();
    }
}