package com.bam.board_service.mapper;

import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserListDTO;
import com.bam.board_service.dto.user.UserUpdateDTO;
import com.bam.board_service.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * UserDTO들과 UserEntity간의 변환을 수행하는 매퍼 클래스
 * @author bam
 * @version 1.0
 */
@Component
public class UserMapper {

    /**
     * DB 저장을 위해 회원가입 폼을 통해 입력받은 UserCreateDTO를 UserEntity로 변환하는 매퍼 메소드
     * @param userCreateDTO 회원가입 폼에 입력된 정보를 토대로 생성된 DTO
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
     * 사용자 정보 수정 폼을 통해 입력받은 userUpdateDTO를 userEntity로 변환하는 매퍼 메소드
     * <p>
     *     변경될 가능성이 있는 정보인 nickname과 password는 userUpdateDTO로 부터 받아오고,
     *     변경되지 않는 정보는 기존 사용자 정보를 가진 originalUserEntity로부터 받아온다.
     * </p>
     * @param originalUserEntity 변경 대상 사용자 Entity
     * @param userUpdateDTO 수정 폼에 입력된 정보를 토대로 생성된 DTO
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

    /**
     * 사용자 목록을 표시하기 위해 UserEntity를 UserListDTO로 변환하는 매퍼 메소드
     * @param userEntity DB에서 조회되어 전달된 UserEntity
     * @return UserListDTO
     */
    public UserListDTO toUserListDTO(UserEntity userEntity) {
        UserListDTO userListDTO = UserListDTO.builder()
            .id(userEntity.getId())
            .username(userEntity.getUsername())
            .nickname(userEntity.getNickname())
            .build();

        return userListDTO;
    }
}
