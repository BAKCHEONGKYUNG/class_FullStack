package com.kh.app.web;

import com.kh.app.domain.entity.Member;
import com.kh.app.domain.member.svc.MemberSVC;
import com.kh.app.web.form.login.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    //login 할 경우 유저의 상태를 관리해야 한다.
    //로그인 성공 시, 내부 번호로 확인
    //로그아웃 버튼 클릭 시, 세션 종료/세션정보는 없어짐
    //그냥 창을 닫을 시, server에서는 시간을 설정.(약 30분. 가장최근에 요청했던걸 참고로)
    //세션 관리는 내일.


    //참조할것
    private final MemberSVC memberSVC;

    //로그인 화면
    @GetMapping("/login")
    public String loginForm(Model model){   //model이라는 빈객체를 만들어 준다.
        //login.html 을 렌더링 할때 "login"이라는 view단에서 model 빈객체를 넣는다.

        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    //로그인 처리
    @PostMapping("/login")
    public String login(
            //요긴 필드 레베르
            @Valid @ModelAttribute
            LoginForm loginForm,
            BindingResult bindingResult, //오류를 저장하는 객체
            HttpServletRequest httpServletRequest,
            @RequestParam(
                    value = "redirectUrl",
                    required = false,
                    defaultValue = "/"
            )
            String redirectUrl
    ) {
        log.info("redirectUrl={}", redirectUrl);

        //오류가 엄쓰면
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "login";
        }

        //오류확인
        //1)아이디 존재유무(중복확인)
        if (!memberSVC.isExist(loginForm.getEmail())) {
            //필드레벨의 오류로 반영
            bindingResult.rejectValue("email", "login", "아이디가 존재하지 않습니다.");

            return "login";
        }

        //2)로그인 (아이디 비밀번호, 아이디가 없는데 비밀번호를 찾을경우가 이쓰니깐
        Optional<Member> member = memberSVC.login(loginForm.getEmail(), loginForm.getPasswd());
        if (member.isEmpty()) {
            bindingResult.rejectValue("passwd", "login", "비밀번호가 일치하지 않습니다.");
            return "login";
        }

        //3)세션생성
        //세션이 있으면 해당 정보를 가져오고 없으면 세션 생성
        HttpSession session = httpServletRequest.getSession(true);
        LoginMember loginMember = new LoginMember(
                member.get().getMemberId(),
                member.get().getEmail(),
                member.get().getNickname(),
                member.get().getGubun()
        );
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:" + redirectUrl;
    }

    //로그아웃
    @GetMapping("logout")
    public String logout(
            HttpServletRequest httpServletRequest
    ){
        //세션이 있으면 해당 정보를 가져오고 없으면 세션 생성 하지 않음
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null){
            session.invalidate();   //세션제거
        }

        return "redirect:/";
    }
}
