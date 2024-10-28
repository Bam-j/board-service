package com.bam.board_service.user;

import com.bam.board_service.dto.UserDTO;
import com.bam.board_service.entity.UserEntity;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    @DisplayName("UserDTO -> UserEntity 변환시 데이터가 제대로 넘어갔는지 확인")
    void userDTOtoUserEntityTest() {
        //given
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "tester1", "tester1", "1234", 0L, 0L);

        //when
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);

        //then
        //각 필드의 값 뿐만 아니라 타입까지 제대로 넘어가야 테스트 성공
        assertEquals(userDTO.getId(), userEntity.getId());
        assertEquals(userDTO.getUsername(), userEntity.getUsername());
        assertEquals(userDTO.getNickname(), userEntity.getNickname());
        assertEquals(userDTO.getPassword(), userEntity.getPassword());
        assertEquals(userDTO.getUserType(), userEntity.getUserType());
        assertEquals(userDTO.getLoginState(), userEntity.getLoginState());
    }
}