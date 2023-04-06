package com.kh.app.web;

import com.kh.app.web.form.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/test/members")
public class TestController {

    // http://localhost:9080/test/members/{id}/{age} => @PathVariable("id")
    //브라우저, 포스트맨 띄워서 확인 가능
    @ResponseBody   //반환값을 메시지 바디에 직접 작성하겠다.
    @GetMapping("/{id}/{age}")
    public String case1(
            @PathVariable("id") String id,
            @PathVariable("age") Long age
    ){
        log.info("id={}, age={}", id, age);
        return id+":"+age;
    }

    // http://localhost:9080/test/members?id=회원아이디&&age=30 => @PathVariable("id")
    //브라우저, 포스트맨 띄워서 확인 가능
    @ResponseBody   //반환값을 메시지 바디에 직접 작성하겠다.
    @GetMapping
    public String case2(
            @PathVariable("id") String id,
            @PathVariable("age") Long age
    ){
        log.info("id={}, age={}", id, age);
        return id+":"+age;
    }

    // http://localhost:9080/test/members
    //브라우저, 포스트맨 띄워서 확인 가능
    @ResponseBody   //반환값을 메시지 헤더에 직접 작성하겠다.
    @GetMapping("/header")
    public String case3(
            @RequestHeader("id") String id,
            @RequestHeader("age") Long age
    ){
        log.info("id={}, age={}", id, age);
        return id+":"+age;
    }

    // http://localhost:9080/test/members
    // form, post요청 => @RequestParam
    //브라우저, 포스트맨 띄워서 확인 가능
    @ResponseBody   //반환값을 메시지 헤더에 직접 작성하겠다.
    @PostMapping
    public String case4(
            @RequestParam("id") String id,
            @RequestParam("age") Long age
    ){
        log.info("id={}, age={}", id, age);
        return id+":"+age;
    }


    // http://localhost:9080/test/members
    // form, post요청 => @ModelAttribute, form정의
    //브라우저, 포스트맨 띄워서 확인 가능
    @ResponseBody   //반환값을 메시지 헤더에 직접 작성하겠다.
    @PostMapping("/object") //http://localhost:9080/test/members/object
    public String case5(
            @ModelAttribute Member member
    ){
        log.info("id={}, age={}", member.getName(), member.getAge());
        return member.getName()+":"+member.getAge();
    }


    // http://localhost:9080/test/members
    // form, post요청 / json => @ResponseBody
    //브라우저, 포스트맨 띄워서 확인 가능 raw, JSON {"key":"value"}
    @ResponseBody   //반환값을 메시지 헤더에 직접 작성하겠다.
    @PostMapping("/object/json") //http://localhost:9080/test/members/object
    public String case6(
            @RequestBody Member member
    ){
        log.info("id={}, age={}", member.getName(), member.getAge());
        return member.getName()+":"+member.getAge();
    }


    // http://localhost:9080/test/members
    // 응답, json => json
    //브라우저, 포스트맨 띄워서 확인 가능 raw, JSON {"key":"value"}
    @ResponseBody   //반환값을 메시지 헤더에 직접 작성하겠다.
    @PostMapping("/object/json2") //http://localhost:9080/test/members/object
    public Member case7(
            @RequestBody Member member
    ){
        log.info("id={}, age={}", member.getName(), member.getAge());
        return member;
    }


    // form, post 요청, json => @RequestBody
    // 응답 json => json
    //브라우저, 포스트맨 띄워서 확인 가능 raw, JSON {"key":"value"}
    @ResponseBody   //반환값을 메시지 헤더에 직접 작성하겠다.
    @PostMapping("/object/json3") //http://localhost:9080/test/members/object
    public RestResponse<Object> case8(
            @RequestBody Member member
    ){
        log.info("id={}, age={}", member.getName(), member.getAge());

        RestResponse<Object> res = null;

        res = RestResponse.createRestResponse("00", "성공", member);

        return res;
    }

    // 2개 이상의 요청 url 받기
    //http://localhost:9080/test/members/case2
    @ResponseBody
    @GetMapping({"/case1", "/case2", "/case3"})
    public String case9(){
        return "ok";
    }

    //응답메세지
    //view + 데이터(thymeleaf)
    //form, post 요청 json => @RequestBody
    @PostMapping("/object2")
    public String case10(
            @ModelAttribute Member member, Model model   //form객체로 받는다
    ){

        log.info("id={}, age={}", member.getName(), member.getAge());

        model.addAttribute("var1", "박청경");
        model.addAttribute("var2", "30");
        model.addAttribute("var3", "살");

//        컬렉션 객체 사용
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        //view에서 접근
        model.addAttribute("list", list);


        //model.addAttribute(Member, member);

        return "test/view";     // 템플릿경로는 .html 빼고 작성
    }
}
