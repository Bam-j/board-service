package com.bam.board_service.comment.service;

import com.bam.board_service.dto.comment.CommentWriteDTO;
import com.bam.board_service.repository.BoardRepository;
import com.bam.board_service.repository.CommentRepository;
import com.bam.board_service.service.CommentService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentDeleteServiceTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("삭제 후 조회시 null을 반환하는지 테스트")
    void returnNullAfterDeleteCommentWhenFindByIdTest() {
        //given
        CommentWriteDTO commentWriteDTO = CommentWriteDTO.builder()
            .writer("tester")
            .comment("test")
            .build();
        UUID id = commentService.save(commentWriteDTO);

        //when
        commentRepository.deleteById(id);

        //then
        assertTrue(commentRepository.findById(id).isEmpty());
    }

    @Test
    @DisplayName("CommentService.delete() 실패 테스트 - 삭제하려는 id의 comment가 없음")
    void commentServiceDeleteMethodFailureByWrongIdTest() {
        //given
        CommentWriteDTO commentWriteDTO = CommentWriteDTO.builder()
            .writer("tester")
            .comment("test")
            .build();
        UUID id = commentService.save(commentWriteDTO);

        UUID randomId = UUID.randomUUID();

        //when
        commentService.delete(randomId);

        //then
        assertTrue(commentRepository.findById(id).isPresent());
    }

    @Test
    @DisplayName("CommentService.delete() 성공 테스트")
    void commentServiceDeleteMethodSuccessTest() {
        //given
        CommentWriteDTO commentWriteDTO = CommentWriteDTO.builder()
            .writer("tester")
            .comment("test")
            .build();
        UUID id = commentService.save(commentWriteDTO);

        //when
        commentService.delete(id);

        //then
        assertTrue(commentRepository.findById(id).isEmpty());
    }
}