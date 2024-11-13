package com.bam.board_service.repository;

import com.bam.board_service.entity.PostEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<PostEntity, UUID> {

    Optional<PostEntity> findByWriter(String writer);

    @Modifying
    @Query(value = "UPDATE PostEntity SET views = views+1 WHERE id=:id")
    void increaseViews(@Param("id") UUID id);
}