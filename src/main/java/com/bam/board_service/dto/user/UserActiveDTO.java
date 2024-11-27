package com.bam.board_service.dto.user;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 페이지에서 로그인 후 로그인한 유저의 정보를 가지고 있는 DTO
 * <p>
 *     페이지 헤더의 유저 정보 등에 사용된다.
 * </p>
 * @author bam
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserActiveDTO {
    private UUID id;
    private String nickname;
    private Long userType;
    private Long loginState;
}
