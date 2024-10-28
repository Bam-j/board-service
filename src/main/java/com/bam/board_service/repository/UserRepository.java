package com.bam.board_service.repository;

import com.bam.board_service.entity.UserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * users_table에 접근하기 위한 리포지토리 인터페이스
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

}
