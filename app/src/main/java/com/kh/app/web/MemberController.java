package com.kh.app.web;

import com.kh.app.domain.common.util.CommonCode;
import com.kh.app.domain.entity.Code;
import com.kh.app.domain.entity.Member;
import com.kh.app.domain.member.svc.MemberSVC;
import com.kh.app.web.common.CodeDecode;
import com.kh.app.web.form.member.JoinForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members") //요청 url, 요기서 url을 찾겠다.
public class MemberController {

    private final MemberSVC memberSVC;
    private final CommonCode commonCode;

    @ModelAttribute("hobbies") //모든 view에서 "code"로 접근이 가능하다.
    public List<CodeDecode> hobbies(){
        List<CodeDecode> codes = new ArrayList<>();

//        codes.add(new CodeDecode("독서", "독서"));
//        codes.add(new CodeDecode("수영", "수영"));
//        codes.add(new CodeDecode("걷기", "걷기"));
//        codes.add(new CodeDecode("골프", "골프"));

        List<Code> findedCodes = commonCode.findCodeByPcodeId("A01");

        //case1)
        //findedCodes.stream()
        //        .forEach(ele -> codes.add(new CodeDecode(ele.getCodeId(), ele.getDecode())));

        //case2)
        for(Code code: findedCodes){
            codes.add(new CodeDecode(code.getCodeId(), code.getDecode()));
        }
        return codes;
    }


    @ModelAttribute("regions") //모든 view에서 "code"로 접근이 가능하다.
    public List<CodeDecode> regions(){
        List<CodeDecode> codes = new ArrayList<>();
//        codes.add(new CodeDecode("서울", "서울"));
//        codes.add(new CodeDecode("부산", "부산"));
//        codes.add(new CodeDecode("대구", "대구"));
//        codes.add(new CodeDecode("울산", "울산"));

        List<Code> findedCodes = commonCode.findCodeByPcodeId("A02");
        findedCodes.stream()
                .forEach(ele ->
                        codes.add(new CodeDecode(ele.getCodeId(), ele.getDecode())));
        return codes;
    }

    //SSR방식,
    //반환타입 String

    //회원가입양식(Responce)
    @GetMapping("/add") // members/add
    public String joinForm(Model model){
        model.addAttribute("joinForm", new JoinForm());
        return "member/joinForm";    //member일때~
    }

    //회원가입처리(데이터 검증)_JoinForm.java
    @PostMapping("/add") // members/add
    public String join(
            @Valid @ModelAttribute JoinForm joinForm,   //JoinForm 이름 아님, 타입임
            //string boot 에서 Model model 사용시
            //model.addAttribute("joinFrom", joinForm);
            BindingResult bindingResult     //오류를 담는 객체
    ){
        log.info("joinForm={}", joinForm);

        //오류정보 확인
        if(bindingResult.hasErrors()){
            log.info("bindingResult={}", bindingResult);
            return "member/joinForm";
        }

        //비밀번호 체크
        if(!joinForm.getPasswd().equals(joinForm.getPasswdchk())){
            bindingResult.reject("passwd","비밀번호가 일치하지않습니다.");
            log.info("bindingResult={}", bindingResult);
            return "member/joinForm";
        }

        //맴버객체 생성 후 전달하기
        Member member = new Member();
        member.setEmail(joinForm.getEmail());
        member.setPasswd(joinForm.getPasswd());
        member.setNickname(joinForm.getNickname());
        member.setGender(joinForm.getGender());
        //hobby의 경우, 문자열로 전달하기에 conversion으로 넘긴다.
        member.setHobby(hobbyToString(joinForm.getHobby()));
        member.setRegion(joinForm.getRegion());

        //SVC 단에 넘기기
        memberSVC.save(member);
        return "member/joinSuccess";    //가입성공 페이지
    }

    //set Hobby 를 통해서 문자열로 받아주기에 String
    private String hobbyToString(List<String> hobby) {
        //map 다른 값으로 바꿔주는
        //reduce 쭉 돌면서, 하나의 값으로 반환
        //forEach 하나씩 꺼내서 문자열로 연결(내부적으로 처리 후 반환

        return StringUtils.join(hobby, ",");
    }
}
