package com.bam.board_service.controller;

import com.bam.board_service.dto.board.PostEditDTO;
import com.bam.board_service.dto.board.PostViewDTO;
import com.bam.board_service.dto.board.PostWriteDTO;
import com.bam.board_service.entity.PostEntity;
import com.bam.board_service.repository.BoardRepository;
import com.bam.board_service.service.BoardService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        boardService.increaseViews(id);

        PostViewDTO postViewDTO = boardService.findById(id);
        model.addAttribute("post", postViewDTO);

        return "/board/postView";
    }

    @GetMapping("/post/edit/{id}")
    public String editForm(@PathVariable UUID id, Model model) {
        //기존 post의 내용을 가져와서 입력폼에 띄워준다.
        //TODO: BoardService에 postEditDTO 취득 과정을 별도의 메소드로 분리하도록 리팩토링
        PostEntity originalPostEntity = boardRepository.findById(id).get();
        PostEditDTO postEditDTO = PostEditDTO.builder()
            .id(originalPostEntity.getId())
            .title(originalPostEntity.getTitle())
            .contents(originalPostEntity.getContents())
            .build();

        model.addAttribute("post", postEditDTO);

        return "/board/postEdit";
    }

    @PostMapping("/post/edit")
    public String edit(@ModelAttribute PostEditDTO postEditDTO, Model model) {
        Boolean editResult = boardService.edit(postEditDTO);

        if (editResult) {
            //model.addAttribute("post", postViewDTO);

            return "/board/postView";
        } else {
            return "redirect:/board/postEdit";
        }
    }
}