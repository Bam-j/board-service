package com.bam.board_service.comment.entity;

import com.bam.board_service.dto.comment.CommentWriteDTO;
import com.bam.board_service.entity.CommentEntity;
import com.bam.board_service.mapper.CommentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    @Test
    @DisplayName("CommentWriteDTO -> CommentEntity 매퍼 변환 테스트")
    void commentWriteDTOToEntityMapperTest() {
        //given
        CommentWriteDTO commentWriteDTO = CommentWriteDTO.builder()
            .writer("tester")
            .comment("test")
            .build();
        CommentMapper commentMapper = new CommentMapper();

        //when
        CommentEntity commentEntity = commentMapper.toCommentEntity(commentWriteDTO);

        //then
        assertAll(
            () -> assertEquals(commentEntity.getWriter(), commentWriteDTO.getWriter()),
            () -> assertEquals(commentEntity.getComment(), commentWriteDTO.getComment())
        );
    }
}