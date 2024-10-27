package com.bam.board_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 메인 페이지인 index.html에 대한 컨트롤러를 정의하는 컨트롤러
 *
 * @author bam
 * @version 1.0
 */
@Controller
public class HomeController {

    /**
     * 메인 페이지인 index.html을 요청하는 메소드
     * @return index.html을 GET 요청
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
