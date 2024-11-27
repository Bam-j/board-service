package com.bam.board_service.controller;

import com.bam.board_service.dto.board.PostListDTO;
import com.bam.board_service.dto.user.UserListDTO;
import com.bam.board_service.service.BoardService;
import com.bam.board_service.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 관리자 페이지에 관련된 요청들을 처리하는 컨트롤러
 *
 * @author bam
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BoardService boardService;

    /**
     * 관리자 페이지의 회원 관리 페이지를 요청하는 메소드
     * <p>
     *     요청 성공시 DB의 모든 유저 정보들을 조회하여 List로 담아서 보낸다.
     * </p>
     * @param model
     * @return manageUsers.html
     */
    @GetMapping("/admin/users")
    public String manageUsers(Model model) {
        List<UserListDTO> userList = userService.findAllUsers();
        model.addAttribute("users", userList);

        return "admin/manageUsers";
    }

    /**
     * 관리자 페이지의 게시글 관리 페이지를 요청하는 메소드
     * <p>
     *     요청 성공시 DB의 모든 게시글들을 조회하여 List로 담아서 보낸다.
     * </p>
     * @param model
     * @return managePosts.html
     */
    @GetMapping("admin/posts")
    public String managePosts(Model model) {
        List<PostListDTO> postList = boardService.findAllPosts();
        model.addAttribute("posts", postList);

        return "admin/managePosts";
    }
}