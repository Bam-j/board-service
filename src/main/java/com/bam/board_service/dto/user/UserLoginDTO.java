package com.bam.board_service.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인시 폼에 입력한 유저 정보를 전달하는 DTO
 * <p>
 *     username, password 두 가지 정보만 가짐
 * </p>
 * @author bam
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserLoginDTO {
    private String username;
    private String password;
}
