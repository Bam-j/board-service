package com.bam.board_service.service;

import com.bam.board_service.dto.comment.CommentListDTO;
import com.bam.board_service.dto.comment.CommentWriteDTO;
import com.bam.board_service.entity.CommentEntity;
import com.bam.board_service.mapper.CommentMapper;
import com.bam.board_service.repository.CommentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Comment 요청과 관련된 로직을 수행하는 서비스 클래스
 * @author bam
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 작성된 댓글을 저장하는 메소드
     * @param commentWriteDTO
     * @return UUID
     */
    public UUID save(CommentWriteDTO commentWriteDTO) {
        CommentMapper commentMapper = new CommentMapper();

        if (commentWriteDTO.getComment().trim().isEmpty()) {
            return null;
        }

        CommentEntity commentEntity = commentMapper.toCommentEntity(commentWriteDTO);

        commentRepository.save(commentEntity);

        return commentEntity.getId();
    }

    /**
     * 댓글 전체 목록을 조회하는 메소드
     * @return List<CommentListDTO>
     */
    @Transactional(readOnly = true)
    public List<CommentListDTO> findAllComments() {
        //TODO: 등록순으로 부여된 컬럼을 만들까? createdTime을 쓸까
        //SELECT * FROM comments_table WHERE post_id=? ORDER BY createdTime DESC;
        List<CommentEntity> commentEntityList = commentRepository.findAll();
        List<CommentListDTO> commentListDTOList = new ArrayList<>();
        CommentMapper commentMapper = new CommentMapper();

        for (CommentEntity commentEntity : commentEntityList) {
            commentListDTOList.add(commentMapper.toCommentListDTO(commentEntity));
        }

        return commentListDTOList;
    }

    /**
     * 댓글 삭제를 처리하는 메소드
     * @param id
     */
    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }
}