package com.bam.board_service.mapper;

import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toUserEntity(UserCreateDTO userCreateDTO) {
        UserEntity userEntity = UserEntity.builder()
            .username(userCreateDTO.getUsername())
            .nickname(userCreateDTO.getNickname())
            .password(userCreateDTO.getPassword())
            .build();

        return userEntity;
    }
}
