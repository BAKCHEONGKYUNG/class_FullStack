package com.kh.app.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/") //클래스 레벨에서 사용
public class HomeController {

    @GetMapping
    public String homt(){
        return "index";
    }

}
