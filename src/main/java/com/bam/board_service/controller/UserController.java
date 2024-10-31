package com.bam.board_service.controller;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
import com.bam.board_service.repository.UserRepository;
import com.bam.board_service.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 회원(user)에 관련된 요청들을 처리하는 컨트롤러
 *
 * @author bam
 * @version 1.0
 */
@Controller
//@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * 회원가입 폼을 요청하는 메소드
     * @return joinForm.html을 반환
     */
    @GetMapping("/user/join")
    public String joinForm() {
        return "/user/joinForm";
    }

    /**
     * joinForm에서 POST 요청이 발생하면 폼 입력 데이터 userDTO로 받아 DB에 저장 후 홈으로 이동
     *
     * @param userCreateDTO
     * @return index.html
     */
    @PostMapping("/user/join")
    public String join(@ModelAttribute UserCreateDTO userCreateDTO) {
        userService.save(userCreateDTO);

        return "index";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "/user/loginForm";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserLoginDTO userLoginDTO) {
        UserActiveDTO loginUser = userService.login(userLoginDTO);

        if (loginUser != null) {
            return "redirect:index";
        }
        else {
            return "/user/loginForm";
        }
    }
}
