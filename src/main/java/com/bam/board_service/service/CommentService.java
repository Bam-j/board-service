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

@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public UUID save(CommentWriteDTO commentWriteDTO) {
        CommentMapper commentMapper = new CommentMapper();

        if (commentWriteDTO.getComment().trim().isEmpty()) {
            return null;
        }

        CommentEntity commentEntity = commentMapper.toCommentEntity(commentWriteDTO);

        commentRepository.save(commentEntity);

        return commentEntity.getId();
    }

    @Transactional(readOnly = true)
    public List<CommentListDTO> findAllComments() {
        List<CommentEntity> commentEntityList = commentRepository.findAll();
        List<CommentListDTO> commentListDTOList = new ArrayList<>();
        CommentMapper commentMapper = new CommentMapper();

        for (CommentEntity commentEntity : commentEntityList) {
            commentListDTOList.add(commentMapper.toCommentListDTO(commentEntity));
        }

        return commentListDTOList;
    }
}