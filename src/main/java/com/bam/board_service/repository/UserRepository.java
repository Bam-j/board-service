package com.bam.board_service.repository;

import com.bam.board_service.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * users_table에 접근하기 위한 리포지토리 인터페이스
 * @author bam
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    /**
     * username을 기반으로 DB 테이블을 탐색하는 메소드
     * <p>
     *     SELECT * FROM users_table WHERE username=?
     * </p>
     * @param username
     * @return Optional<UserEntity>
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * nickname을 기반으로 DB 테이블을 탐색하는 메소드
     * <p>
     *     SELECT * FROM users_table WHERE nickname=?
     * </p>
     * @param nickname
     * @return Optional<T>
     */
    Optional<UserEntity> findByNickname(String nickname);
}
