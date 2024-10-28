package com.bam.board_service.entity;

import com.bam.board_service.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

/**
 * 유저 정보들을 담은 DB의 users_table에 접근하기 위한 엔티티 클래스
 *
 * @author bam
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;


    @Column(unique = true, nullable = false, length = 20)
    private String nickname;


    @Column(nullable = false)
    private String password;

    /**
     * 일반 사용자와 관리자 계정을 구분하는 필드. 0: 일반 사용자 계정, 1: 관리자 계정
     */
    @Column(nullable = false)
    private Long userType;


    /**
     * 로그인 상태를 나타내기 위한 필드. 0: 로그인 중이 아님, 1: 로그인 중
     */
    @Column(nullable = false)
    private Long loginState;

    /**
     * 빌더 패턴이 적용된 생성자
     *
     * @param id
     * @param username
     * @param nickname
     * @param password
     * @param userType
     * @param loginState
     */
    @Builder
    public UserEntity(UUID id, String username, String nickname, String password, Long userType,
        Long loginState) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.userType = userType;
        this.loginState = loginState;
    }

    /**
     * UserDTO 객체를 받아 UserEntity로 변경하는 메소드
     *
     * @param userDTO
     * @return userEntity
     */
    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.builder()
            .id(userDTO.getId())
            .username(userDTO.getUsername())
            .nickname(userDTO.getNickname())
            .password(userDTO.getPassword())
            .userType(userDTO.getUserType())
            .loginState(userDTO.getLoginState())
            .build();

        return userEntity;
    }
}
