package com.bam.board_service.controller;

import com.bam.board_service.dto.board.PostListDTO;
import com.bam.board_service.service.BoardService;
import java.util.List;
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
    @GetMapping("/home")
    public String index(Model model) {
        List<PostListDTO> posts = boardService.findAllPosts();
        model.addAttribute("posts", posts);

        return "index";
    }
}
