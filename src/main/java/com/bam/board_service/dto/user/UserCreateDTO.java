package com.bam.board_service.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원가입시 폼에 입력한 유저 정보를 전달하는 DTO
 * <p>
 *     UUID, userType, loginState는 저장시에 기본값으로 전달된다.
 *     따라서 username, nickname, password 세 가지 필드를 갖는다.
 * </p>
 * @author bam
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserCreateDTO {
    private String username;
    private String nickname;
    private String password;
}
