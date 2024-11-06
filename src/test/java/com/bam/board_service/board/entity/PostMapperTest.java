package com.bam.board_service.board.entity;

import com.bam.board_service.dto.board.PostListDTO;
import com.bam.board_service.entity.PostEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {

    @Test
    @DisplayName("PostMapper.toPostListDTO() 테스트")
    void postEntityToPostListDTOMapperTest() {
        //given
        PostListDTO postListDTO = PostListDTO.builder()
            .writer("test")
            .title("test")
            .contents("test")
            .build();
        PostMapper postMapper = new PostMapper();

        //when
        PostEntity postEntity = postMapper.toPostEntity(postListDTO);

        //then
        assertEquals(postEntity.getWriter(), postListDTO.getWriter());
        assertEquals(postEntity.getTitle(), postListDTO.getTitle());
        assertEquals(postEntity.getContents(), postListDTO.getContents());
    }
}