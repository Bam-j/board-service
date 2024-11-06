package com.bam.board_service.mapper;

import com.bam.board_service.dto.board.PostListDTO;
import com.bam.board_service.entity.PostEntity;

public class PostMapper {

    public PostListDTO toPostListDTO(PostEntity postEntity) {
        PostListDTO postListDTO = PostListDTO.builder()
            .writer(postEntity.getWriter())
            .title(postEntity.getTitle())
            .contents(postEntity.getContents())
            .views(postEntity.getViews())
            .createdTime(postEntity.getCreatedTime())
            .build();

        return postListDTO;
    }
}
