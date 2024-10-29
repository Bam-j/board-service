package com.bam.board_service.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 페이지에서 로그인 상태와 닉네임을 전달하는 DTO
 * @author bam
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserActiveDTO {
    private String nickname;
    private Long loginState;
}
