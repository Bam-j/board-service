package com.bam.board_service.mapper;

import com.bam.board_service.dto.comment.CommentListDTO;
import com.bam.board_service.dto.comment.CommentWriteDTO;
import com.bam.board_service.entity.CommentEntity;

/**
 * CommentDTO와 CommentEntity간의 변환을 수행하는 매퍼 클래스
 * @author bam
 * @version 1.0
 */
public class CommentMapper {

    /**
     * 댓글 작성 후 저장을 위해 CommentWriteDTO를 CommentEntity로 변환하는 매퍼 메소드
     * @param commentWriteDTO 수정 폼에 입력된 내용을 토대로 생성된 DTO
     * @return CommentEntity
     */
    public CommentEntity toCommentEntity(CommentWriteDTO commentWriteDTO) {
        CommentEntity commentEntity = CommentEntity.builder()
            .writer(commentWriteDTO.getWriter())
            .comment(commentWriteDTO.getComment())
            .build();

        return commentEntity;
    }

    /**
     * 댓글 목록 조회를 위해 CommentEntity를 CommentListDTO로 변환하는 매퍼 메소드
     * @param commentEntity DB에서 조회되어 전달된 CommentEntity
     * @return CommentListDTO
     */
    public CommentListDTO toCommentListDTO(CommentEntity commentEntity) {
        CommentListDTO commentListDTO = CommentListDTO.builder()
            .id(commentEntity.getId())
            .writer(commentEntity.getWriter())
            .comment(commentEntity.getComment())
            .createdTime(commentEntity.getCreatedTime())
            .build();

        return commentListDTO;
    }
}
