package com.bam.board_service.controller;

import com.bam.board_service.dto.comment.CommentListDTO;
import com.bam.board_service.dto.comment.CommentWriteDTO;
import com.bam.board_service.service.CommentService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

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

    @PostMapping("/comment/delete")
    public ResponseEntity delete(UUID id) {
        commentService.delete(id);

        List<CommentListDTO> commentListDTOList = commentService.findAllComments();

        return new ResponseEntity<>(commentListDTOList, HttpStatus.OK);
    }
}