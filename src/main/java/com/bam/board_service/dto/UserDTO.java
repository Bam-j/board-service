package com.bam.board_service.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 계층간 유저 데이터를 교환하기 위한 객체
 * @author bam
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private UUID id;
    private String username;
    private String nickname;
    private String password;
    /**
     * 일반 사용자와 관리자 계정을 구분하는 필드.
     * 0: 일반 사용자 계정, 1: 관리자 계정
     */
    private Long userType;
    /**
     * 로그인 상태를 나타내기 위한 필드.
     * 0: 로그인 중이 아님, 1: 로그인 중
     */
    private Long loginState;

    /**
     * 빌더 패턴이 적용된 생성자
     * @param username
     * @param nickname
     * @param password
     */
    @Builder
    public UserDTO(String username, String nickname, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.userType = 0L;
        this.loginState = 0L;
    }
}
