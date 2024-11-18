package com.bam.board_service.mapper;

import com.bam.board_service.dto.comment.CommentWriteDTO;
import com.bam.board_service.entity.CommentEntity;

public class CommentMapper {
    public CommentEntity toCommentEntity(CommentWriteDTO commentWriteDTO) {
        CommentEntity commentEntity = CommentEntity.builder()
            .writer(commentWriteDTO.getWriter())
            .comment(commentWriteDTO.getComment())
            .build();

        return commentEntity;
    }
}
