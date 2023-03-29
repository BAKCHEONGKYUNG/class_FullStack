package com.kh.app.domain.member.dao;

import com.kh.app.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO{

    private final NamedParameterJdbcTemplate template;

    /**
     * 가입
     * @param member
     * @return
     */
    @Override
    public Member save(Member member) {

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO MEMBER ( ");
        sql.append("        MEMBER_ID, ");
        sql.append("        EMAIL, ");
        sql.append("        PASSWD, ");
        sql.append("        NICKNAME, ");
        sql.append("        GENDER, ");
        sql.append("        HOBBY, ");
        sql.append("        REGION ");
        sql.append(") VALUES ( ");
        sql.append("        MEMBER_MEMBER_ID_SEQ.NEXTVAL, ");
        sql.append("        :email, ");
        sql.append("        :passwd, ");
        sql.append("        :nickname, ");
        sql.append("        :gender, ");
        sql.append("        :hobby, ");
        sql.append("        :region ");
        sql.append(") ");

        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        KeyHolder keyHolder = new GeneratedKeyHolder(); //insert된 레코드에서 컬럼값 추출
        template.update(sql.toString(), param, keyHolder, new String[]{"member_id"});

        long memberId = keyHolder.getKey().longValue();

        member.setMemberId(memberId);

        return member;
    }

    /**
     * 수정
     * @param memberId
     * @param member
     */
    @Override
    public void update(Long memberId, Member member) {

    }

    /**
     * @param email
     * @return
     */
    @Override
    public Member findByEmail(String email) {
        return null;
    }

    /**
     * @param memberId
     * @return
     */
    @Override
    public Member findById(String memberId) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<Member> findAll() {
        return null;
    }

    /**
     * @param email
     */
    @Override
    public void delete(String email) {

    }

    /**
     * @param email
     * @return
     */
    @Override
    public boolean isExist(String email) {
        return false;
    }

    /**
     * @param email
     * @param passwd
     * @return
     */
    @Override
    public Member login(String email, String passwd) {
        return null;
    }

    /**
     * @param nickname
     * @return
     */
    @Override
    public String findEmailByNicknames(String nickname) {
        return null;
    }

    /**
     * @param memberId
     * @param passwd
     * @return
     */
    @Override
    public String findPasswdByIdAndNickname(String memberId, String passwd) {
        return null;
    }
}
