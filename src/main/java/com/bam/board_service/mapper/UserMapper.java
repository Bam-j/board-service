package com.bam.board_service.mapper;

import com.bam.board_service.dto.UserDTO;
import com.bam.board_service.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity userDTOToUserEntity(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.builder()
            .username(userDTO.getUsername())
            .nickname(userDTO.getNickname())
            .password(userDTO.getPassword())
            .build();

        return userEntity;
    }

    public UserDTO userEntityToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = UserDTO.builder()
            .username(userEntity.getUsername())
            .nickname(userEntity.getNickname())
            .password(userEntity.getPassword())
            .build();

        return userDTO;
    }
}
