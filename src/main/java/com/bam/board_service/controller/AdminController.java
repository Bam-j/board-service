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

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BoardService boardService;

    /*
    * UserService의 findAll(), delete(id), BoardService의 findAll(), delete()를 사용
     */
    @GetMapping("/admin/users")
    public String manageUsers(Model model) {
        List<UserListDTO> userList = userService.findAllUsers();
        model.addAttribute("users", userList);

        return "admin/manageUsers";
    }

    @GetMapping("admin/posts")
    public String managePosts(Model model) {
        List<PostListDTO> postList = boardService.findAllPosts();
        model.addAttribute("posts", postList);

        return "admin/managePosts";
    }
}