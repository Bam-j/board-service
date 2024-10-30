package com.bam.board_service.mapper;

import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * UserDTO <-> UserEntity 변환을 수행하는 매퍼 클래스
 */
@Component
public class UserMapper {

    /**
     * 회원가입폼을 통해 입력받은 UserCreateDTO를 UserEntity로 변환
     * <p>
     *     username, nickname, password는 입력받은 데이터를 이용해 엔티티에 전달하고,
     *     userType과 loginState는 기본값인 0L을 넣어서 빌더 생성자를 통해 변환
     * </p>
     * @param userCreateDTO
     * @return userEntity
     */
    public UserEntity toUserEntity(UserCreateDTO userCreateDTO) {
        UserEntity userEntity = UserEntity.builder()
            .username(userCreateDTO.getUsername())
            .nickname(userCreateDTO.getNickname())
            .password(userCreateDTO.getPassword())
            .userType(0L)
            .loginState(0L)
            .build();

        return userEntity;
    }
}
