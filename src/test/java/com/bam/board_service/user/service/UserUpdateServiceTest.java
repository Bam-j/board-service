package com.bam.board_service.user.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserUpdateDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
import com.bam.board_service.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserUpdateServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    @DisplayName("변경하려는 nickname이 이미 DB 내에 존재하는 경우 Optional은 null이면 안된다")
    void userNicknameUpdateFailureTest() {
        //given
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();
        userRepository.save(userMapper.toUserEntity(userCreateDTO));

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .nickname("test")
            .password("1234")
            .build();

        //when
        Optional<UserEntity> optionalUserEntity = userRepository.findByNickname(
            userUpdateDTO.getNickname());

        //then
        assertTrue(optionalUserEntity.isPresent());
    }

    @Test
    @DisplayName("변경하려는 nickname이 DB에 없는 경우 Optional은 null을 반환")
    void userNicknameUpdateSuccessTest() {
        //given
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();
        userRepository.save(userMapper.toUserEntity(userCreateDTO));

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .nickname("tester")
            .password("1234")
            .build();

        //when
        Optional<UserEntity> optionalUserEntity = userRepository.findByNickname(
            userUpdateDTO.getNickname());

        //then
        assertFalse(optionalUserEntity.isPresent());
    }

    @Test
    @DisplayName("비밀번호 변경시 이전 비밀번호와 동일하다면 null 반환")
    void userPasswordUpdateFailureTest() {
        //given
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();
        userRepository.save(userMapper.toUserEntity(userCreateDTO));

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .nickname("test")
            .password("1234")
            .build();

        //when
        Optional<UserEntity> optionalUserEntity = userRepository.findByNickname(
            userUpdateDTO.getNickname());

        UserEntity userEntity = null;

        if (optionalUserEntity.isPresent()) {
            userEntity = optionalUserEntity.get();
            if (userEntity.getPassword().equals(userUpdateDTO.getPassword())) {
                userEntity = null;
            }
        }

        //then
        assertNull(userEntity);
    }

    //TODO: 테스트 코드 리팩토링 때 두 개의 테스트 메소드로 분할
    @Test
    @DisplayName("UserService.update() 실패 테스트")
    void userServiceUpdateMethodFailureTest() {
        //given
        UserCreateDTO userCreateDTO1 = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserCreateDTO userCreateDTO2 = UserCreateDTO.builder()
            .username("hello")
            .nickname("hello")
            .password("1111")
            .build();
        UserMapper userMapper = new UserMapper();
        userRepository.save(userMapper.toUserEntity(userCreateDTO1));
        userRepository.save(userMapper.toUserEntity(userCreateDTO2));

        UserUpdateDTO userUpdateBySameNicknameDTO = UserUpdateDTO.builder()
            .nickname("hello")
            .password("1234")
            .build();
        UserUpdateDTO userUpdateBySamePasswordDTO = UserUpdateDTO.builder()
            .nickname("test")
            .password("1234")
            .build();

        //when
        //case 1. 닉네임 "test"가 이미 존재하는 닉네임인 "hello"로 변경 시도 -> 결과: null
        UserActiveDTO sameNicknameResultUserActiveDTO = userService.update("test",
            userUpdateBySameNicknameDTO);

        //case 2. 닉네임 "test"가 변경 전과 동일한 패스워드로 변경 시도 -> 결과: null
        UserActiveDTO samePasswordResultUserActiveDTO = userService.update("test",
            userUpdateBySamePasswordDTO);

        //then
        assertNull(sameNicknameResultUserActiveDTO);
        assertNull(samePasswordResultUserActiveDTO);
    }

    @Test
    @DisplayName("UserService.update() 성공 테스트")
    void userServiceUpdateMethodSuccessTest() {
        //given
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();
        userRepository.save(userMapper.toUserEntity(userCreateDTO));

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .nickname("hello")
            .password("1234")
            .build();

        //when
        UserActiveDTO userActiveDTO = userService.update("test", userUpdateDTO);

        //then
        assertThat(userActiveDTO).isNotNull();
        assertThat(userActiveDTO.getNickname()).isEqualTo(userUpdateDTO.getNickname());
    }
}