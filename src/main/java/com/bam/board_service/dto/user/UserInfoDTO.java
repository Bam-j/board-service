package com.bam.board_service.dto.user;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 관리자 페이지에서 회원 정보 조회시 회원의 정보를 전달하는 DTO
 * @author bam
 * @version 1.0
 * @deprecated password를 노출할 필요가 없으므로 사용하지 않음. UserListDTO로 대체.
 */
@Deprecated
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserInfoDTO {
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
}
