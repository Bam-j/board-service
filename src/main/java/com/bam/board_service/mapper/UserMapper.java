package com.bam.board_service.mapper;

import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserUpdateDTO;
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

    /**
     * 업데이트 폼을 통해 입력받은 userUpdateDTO를 userEntity로 변환
     * <p>
     *     변경될 가능성이 있는 정보인 nickname과 password는 userUpdateDTO로 부터 받아오고,
     *     변경되지 않는 정보는 기존 사용자 정보를 가진 originalUserEntity로부터 받아온다.
     *     이때 로그인 상태는 1L로 유지해야하므로 직접 값을 준다.
     * </p>
     * @param originalUserEntity
     * @param userUpdateDTO
     * @return UserEntity
     */
    public UserEntity toUserEntity(UserEntity originalUserEntity, UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = UserEntity.builder()
            .id(originalUserEntity.getId())
            .username(originalUserEntity.getUsername())
            .nickname(userUpdateDTO.getNickname())
            .password(userUpdateDTO.getPassword())
            .userType(originalUserEntity.getUserType())
            .loginState(1L)
            .build();

        return userEntity;
    }
}
