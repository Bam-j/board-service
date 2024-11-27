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

/**
 * 게시글에 관련된 요청들을 처리하는 컨트롤러
 *
 * @author bam
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    /**
     * 게시글 작성 페이지를 요청하는 메소드
     * @return postWrite.html
     */
    @GetMapping("/post/write")
    public String writeForm() {
        return "/board/postWrite";
    }

    /**
     * 게시글 작성을 요청하는 메소드
     * <p>
     *     작성 후, 게시글 목록에 해당하는 index 페이지로 이동한다.
     * </p>
     * @param postWriteDTO
     * @return /index
     */
    @PostMapping("/post/write")
    public String write(@ModelAttribute PostWriteDTO postWriteDTO) {
        boardService.save(postWriteDTO);

        return "/index";
    }

    /**
     * 게시글 조회를 요청하는 메소드
     * @param id
     * @param model
     * @return postView.html
     */
    @GetMapping("/post/view/{id}")
    public String postView(@PathVariable UUID id, Model model) {
        boardService.increaseViews(id);

        PostViewDTO postViewDTO = boardService.findById(id);
        model.addAttribute("post", postViewDTO);

        return "/board/postView";
    }

    /**
     * 게시글 수정 페이지를 요청하는 메소드
     * @param id
     * @param model
     * @return postEdit.html
     */
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

    /**
     * 게시글 수정을 요청하는 메소드
     * <p>
     *     요청 성공시 게시글 조회 페이지로 이동하고, 실패시 수정 화면으로 돌아온다.
     * </p>
     * @param postEditDTO
     * @param model
     * @return postView.html or postEdit.html
     */
    @PostMapping("/post/edit")
    public String edit(@ModelAttribute PostEditDTO postEditDTO, Model model) {
        //TODO: boardService.edit의 결과가 Boolean이 아닌 postViewDTO를 반환하도록 변경할 것
        Boolean editResult = boardService.edit(postEditDTO);

        if (editResult) {
            //model.addAttribute("post", postViewDTO);

            return "/board/postView";
        } else {
            return "redirect:/board/postEdit";
        }
    }

    /**
     * 게시글 삭제 요청을 처리하는 메소드
     * @param id
     * @return /index
     */
    @PostMapping("/post/delete/{id}")
    public String delete(@PathVariable UUID id) {
        boardService.delete(id);

        return "/index";
    }
}