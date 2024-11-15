package com.bam.board_service.mapper;

import com.bam.board_service.dto.board.PostEditDTO;
import com.bam.board_service.dto.board.PostListDTO;
import com.bam.board_service.dto.board.PostWriteDTO;
import com.bam.board_service.entity.PostEntity;

public class PostMapper {

    public PostListDTO toPostListDTO(PostEntity postEntity) {
        PostListDTO postListDTO = PostListDTO.builder()
            .id(postEntity.getId())
            .writer(postEntity.getWriter())
            .title(postEntity.getTitle())
            .contents(postEntity.getContents())
            .views(postEntity.getViews())
            .createdTime(postEntity.getCreatedTime())
            .build();

        return postListDTO;
    }

    public PostEntity toPostEntity(PostWriteDTO postWriteDTO) {
        PostEntity postEntity = PostEntity.builder()
            .writer(postWriteDTO.getWriter())
            .title(postWriteDTO.getTitle())
            .contents(postWriteDTO.getContents())
            .views(0L)
            .build();

        return postEntity;
    }

    public PostEntity toPostEntity(PostEntity originalPostEntity, PostEditDTO postEditDTO) {
        PostEntity postEntity = PostEntity.builder()
            .writer(originalPostEntity.getWriter())
            .title(postEditDTO.getTitle())
            .contents(postEditDTO.getContents())
            .views(originalPostEntity.getViews())
            .build();

        return postEntity;
    }
}
