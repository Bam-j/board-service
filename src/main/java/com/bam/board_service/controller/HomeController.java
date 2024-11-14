package com.bam.board_service.controller;

import com.bam.board_service.dto.board.PostListDTO;
import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.service.BoardService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 메인 페이지인 index.html에 대한 컨트롤러를 정의하는 컨트롤러
 *
 * @author bam
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    /**
     * 메인 페이지인 index.html을 요청하는 메소드
     * @return index.html을 GET 요청
     */
    @GetMapping("/")
    public String index(Model model) {
        List<PostListDTO> posts = boardService.findAllPosts();
        //TODO: 현재 화면 뿌리기 용으로 더미 유저 사용중
        UserActiveDTO userActiveDTO = UserActiveDTO.builder()
            .id(UUID.randomUUID())
            .nickname("dummy")
            .loginState(1L)
            .build();
        model.addAttribute("user", userActiveDTO);
        model.addAttribute("posts", posts);

        return "index";
    }
}
