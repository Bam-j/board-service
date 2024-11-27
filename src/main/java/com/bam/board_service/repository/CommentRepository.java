package com.bam.board_service.repository;

import com.bam.board_service.entity.CommentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * comments_table에 접근하기 위한 리포지토리 인터페이스
 * @author bam
 * @version 1.0
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {

}