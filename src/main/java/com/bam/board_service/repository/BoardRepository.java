package com.bam.board_service.repository;

import com.bam.board_service.entity.PostEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * board_table에 접근하기 위한 리포지토리 인터페이스
 * @author bam
 * @version 1.0
 */
@Repository
public interface BoardRepository extends JpaRepository<PostEntity, UUID> {

    /**
     * writer를 기반으로 DB 테이블을 탐색하는 메소드
     * @param writer
     * @return Optional<PostEntity>
     */
    Optional<PostEntity> findByWriter(String writer);

    /**
     * id를 기반으로 DB에서 특정 컬럼을 찾은 후 해당 컬럼의 views를 1 증가시키는 메소드
     * <p>
     *     UPDATE PostEntity SET views = views+1 WHERE id=:id
     * </p>
     * @param id 게시글 조회시 전달되는 해당 게시글의 id
     */
    @Modifying
    @Query(value = "UPDATE PostEntity SET views=views+1 WHERE id=:id")
    void increaseViews(@Param("id") UUID id);
}