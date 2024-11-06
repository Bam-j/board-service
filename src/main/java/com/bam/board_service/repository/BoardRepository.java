package com.bam.board_service.repository;

import com.bam.board_service.entity.BoardEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, UUID> {

}