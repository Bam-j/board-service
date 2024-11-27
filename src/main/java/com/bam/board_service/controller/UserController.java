package com.bam.board_service.controller;

import com.bam.board_service.dto.board.PostListDTO;
import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
import com.bam.board_service.dto.user.UserUpdateDTO;
import com.bam.board_service.repository.UserRepository;
import com.bam.board_service.service.BoardService;
import com.bam.board_service.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 회원에 관련된 요청들을 처리하는 컨트롤러
 *
 * @author bam
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BoardService boardService;

    /**
     * 회원가입 폼을 요청하는 메소드
     * @return joinForm.html
     */
    @GetMapping("/user/join")
    public String joinForm() {
        return "/user/joinForm";
    }

    /**
     * joinForm으로부터 입력 데이터를 받아 User를 DB에 저장한 후 index로 이동*
     * @param userCreateDTO
     * @return index.html
     */
    @PostMapping("/user/join")
    public String join(@ModelAttribute UserCreateDTO userCreateDTO) {
        userService.save(userCreateDTO);

        return "index";
    }

    /**
     * 로그인 폼을 요청하는 메소드
     * @return loginForm.html
     */
    @GetMapping("/user/login")
    public String loginForm() {
        return "/user/loginForm";
    }

    /**
     * loginForm으로부터 입력 데이터를 받아와 로그인 처리를 요청하는 메소드
     * <p>
     *     로그인에 성공하면 세션에 로그인 유저 정보를 저장한 뒤 index로 이동하고, <br>
     *     로그인에 실패하면 로그인 폼의 데이터를 지운다.
     * </p>
     * @param userLoginDTO
     * @return index.html or loginForm.html
     */
    @PostMapping("/user/login")
    public String login(UserLoginDTO userLoginDTO, HttpSession session, Model model) {
        UserActiveDTO user = userService.login(userLoginDTO, session);

        if (user != null) {
            session.setAttribute("user", user);

            List<PostListDTO> posts = boardService.findAllPosts();

            model.addAttribute("posts", posts);

            return "/index";
        } else {
            //TODO: 로그인 실패시 loginForm을 띄우면서 안내메세지 출력하도록 만들기
            return "/user/loginForm";
        }
    }

    /**
     * 사용자 정보 수정 폼을 요청하는 메소드
     * @return updateForm.html
     */
    @GetMapping("/user/update")
    public String updateForm() {
        return "/user/updateForm";
    }

    /**
     * 사용자 nickname 변경 요청을 처리하는 메소드
     * @param userUpdateDTO
     * @param session
     * @return updateForm.html
     */
    @PostMapping("/user/updateNickname")
    public String updateNickname(UserUpdateDTO userUpdateDTO, HttpSession session) {
        UUID id = (UUID) session.getAttribute("loginUserId");
        userService.updateNickname(id, userUpdateDTO);

        return "redirect:/user/updateForm";
    }

    /**
     * 사용자 password 변경 요청을 처리하는 메소드
     * @param userUpdateDTO
     * @param session
     * @return updateForm.html
     */
    @PostMapping("/user/updatePassword")
    public String updatePassword(UserUpdateDTO userUpdateDTO, HttpSession session) {
        UUID id = (UUID) session.getAttribute("loginUserId");
        userService.updatePassword(id, userUpdateDTO);

        return "redirect:/user/updateForm";
    }

    /**
     * 사용자 삭제 요청을 처리하는 메소드
     * @param id
     * @return String deleteResult
     */
    @PostMapping("/user/delete/{id}")
    public String delete(@PathVariable UUID id) {
        String deleteResult = userService.delete(id);

        return deleteResult;
    }

    /**
     * 로그아웃 요청을 처리하는 메소드
     * @param session
     * @return index.html
     */
    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "index";
    }
}
