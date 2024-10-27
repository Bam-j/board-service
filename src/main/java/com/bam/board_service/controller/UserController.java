package com.bam.board_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회원(user)에 관련된 요청들을 처리하는 컨트롤러
 *
 * @author bam
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    /**
     * 회원가입 폼을 요청하는 메소드
     * @return joinForm.html을 반환
     */
    @GetMapping("/join")
    public String joinForm() {
        return "joinForm";
    }
}
