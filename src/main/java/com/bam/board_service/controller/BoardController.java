package com.bam.board_service.controller;

import com.bam.board_service.dto.board.PostWriteDTO;
import com.bam.board_service.repository.BoardRepository;
import com.bam.board_service.service.BoardService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping("/post/write")
    public String writeForm() {
        return "/board/postWrite";
    }

    @PostMapping("/post/write")
    public String write(PostWriteDTO postWriteDTO) {
        boardService.save(postWriteDTO);

        return "redirect:/index";
    }

    @GetMapping("/post/view/{id}")
    public String postView(@PathVariable UUID id, Model model) {
        model.addAttribute("post", boardRepository.findById(id));
        boardService.increaseViews(id);

        return "/board/postView";
    }
}