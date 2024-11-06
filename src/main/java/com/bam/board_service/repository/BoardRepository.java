package com.bam.board_service.repository;

import com.bam.board_service.entity.PostEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<PostEntity, UUID> {

    Optional<PostEntity> findByWriter(String writer);
}