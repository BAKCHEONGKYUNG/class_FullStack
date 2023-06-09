package com.kh.app.domain.member.dao;

import com.kh.app.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
class MemberDAOImplTest {

    @Autowired
    private MemberDAO memberDAO;

    @Test
    @DisplayName("가입")
    void save() {

        Member member = new Member();
        member.setEmail("test2@kh.com");
        member.setPasswd("1234");
        member.setNickname("냥");
        member.setGender("여자");
        member.setHobby("독서");
        member.setRegion("울산");

        Member savedMember = memberDAO.save(member);

        Assertions.assertThat(savedMember.getMemberId()).isGreaterThan(0);
        Assertions.assertThat(savedMember.getEmail()).isEqualTo("test2@kh.com");
        Assertions.assertThat(savedMember.getPasswd()).isEqualTo("1234");
        Assertions.assertThat(savedMember.getNickname()).isEqualTo("냥");
        Assertions.assertThat(savedMember.getGender()).isEqualTo("여자");
        Assertions.assertThat(savedMember.getHobby()).isEqualTo("독서");
        Assertions.assertThat(savedMember.getRegion()).isEqualTo("울산");
    }

    @Test
    @DisplayName("로그인")
    void login(){
        //회원이 존재하는 경우
        Optional<Member> member = memberDAO.login("test2@kh.com", "1234");
        log.info("member={}", member);

        Assertions.assertThat(member.isPresent()).isTrue();

        //회원이 존재하지 않는 경우
        member = memberDAO.login("test2@kh.com", "12345");
        Assertions.assertThat(member.isPresent()).isFalse();
    }
}