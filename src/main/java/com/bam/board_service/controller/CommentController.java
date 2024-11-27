package com.bam.board_service.controller;

import com.bam.board_service.dto.comment.CommentListDTO;
import com.bam.board_service.dto.comment.CommentWriteDTO;
import com.bam.board_service.service.CommentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 게시글 댓글에 관련된 요청들을 처리하는 컨트롤러
 *
 * @author bam
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성 요청을 처리하는 메소드
     * <p>
     *     작성 성공, 실패 결과는 프론트에서 처리한다.
     * </p>
     * @param commentWriteDTO
     * @return ResponseEntity
     */
    @PostMapping("/comment/save")
    public ResponseEntity save(CommentWriteDTO commentWriteDTO) {
        UUID commentSavedResult = commentService.save(commentWriteDTO);

        if (commentSavedResult != null) {
            List<CommentListDTO> commentListDTOList = commentService.findAllComments();

            return new ResponseEntity<>(commentListDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 댓글 삭제 요청을 처리하는 메소드
     * <p>
     *     삭제 성공, 실패 결과는 프론트에서 처리한다.
     * </p>
     * @param id
     * @return ResponseEntity
     */
    @PostMapping("/comment/delete")
    public ResponseEntity delete(UUID id) {
        commentService.delete(id);

        List<CommentListDTO> commentListDTOList = commentService.findAllComments();

        return new ResponseEntity<>(commentListDTOList, HttpStatus.OK);
    }
}