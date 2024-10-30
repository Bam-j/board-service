package com.bam.board_service.user.entity;

import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserEntityTest {

    @Test
    @DisplayName("UserDTO -> UserEntity 변환시 데이터가 제대로 넘어갔는지 확인")
    void userDTOtoUserEntityTest() {
        //given
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();

        //when
        UserEntity userEntity = userMapper.toUserEntity(userCreateDTO);

        //then
        //각 필드의 값 뿐만 아니라 타입까지 제대로 넘어가야 테스트 성공
        assertEquals(userCreateDTO.getUsername(), userEntity.getUsername());
        assertEquals(userCreateDTO.getNickname(), userEntity.getNickname());
        assertEquals(userCreateDTO.getPassword(), userEntity.getPassword());
    }
}