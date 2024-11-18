package com.bam.board_service.comment.service;

import com.bam.board_service.dto.comment.CommentWriteDTO;
import com.bam.board_service.entity.CommentEntity;
import com.bam.board_service.mapper.CommentMapper;
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
class CommentWriteTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("댓글이 저장되는 지 확인")
    void commentSaveTest() {
        //given
        CommentWriteDTO commentWriteDTO = CommentWriteDTO.builder()
            .writer("tester")
            .comment("test")
            .build();
        CommentMapper commentMapper = new CommentMapper();

        //when
        CommentEntity commentEntity = commentMapper.toCommentEntity(commentWriteDTO);
        commentRepository.save(commentEntity);

        //then
        UUID savedCommentId = commentEntity.getId();
        CommentEntity savedComment = commentRepository.findById(savedCommentId).get();

        assertAll(
            () -> assertNotNull(savedCommentId),
            () -> assertEquals(savedComment.getWriter(), commentWriteDTO.getWriter()),
            () -> assertEquals(savedComment.getComment(), commentWriteDTO.getComment())
        );
    }

    @Test
    @DisplayName("CommentService.save() 실패 테스트 - comment가 빈 문자열이면 등록 실패")
    void commentServiceSaveMethodFailureByEmptyStringInCommentTest() {
        //given
        CommentWriteDTO commentWriteDTO = CommentWriteDTO.builder()
            .writer("tester")
            .comment("")
            .build();

        //when
        UUID id = commentService.save(commentWriteDTO);

        //then
        assertNull(id);
    }

    @Test
    @DisplayName("CommentService.save() 성공 테스트")
    void commentServiceSaveMethodSuccessTest() {
        //given
        CommentWriteDTO commentWriteDTO = CommentWriteDTO.builder()
            .writer("tester")
            .comment("test")
            .build();

        //when
        UUID id = commentService.save(commentWriteDTO);

        //then
        assertTrue(commentRepository.findById(id).isPresent());
    }
}