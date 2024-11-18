package com.bam.board_service.comment.service;

import com.bam.board_service.dto.comment.CommentListDTO;
import com.bam.board_service.dto.comment.CommentWriteDTO;
import com.bam.board_service.service.CommentService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentViewTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("게시글 목록 조회 테스트")
    void notNullAfterSaveCommentTest() {
        //given
        CommentWriteDTO commentWriteDTO = CommentWriteDTO.builder()
            .writer("tester")
            .comment("test")
            .build();
        commentService.save(commentWriteDTO);

        //when
        List<CommentListDTO> commentListDTOList = commentService.findAllComments();

        //then
        assertNotNull(commentListDTOList);
    }
}