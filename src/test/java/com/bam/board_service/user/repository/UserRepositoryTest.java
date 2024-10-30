package com.bam.board_service.user.repository;

import com.bam.board_service.dto.user.UserCreateDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("findByUsername 동작 테스트 - DB에 일치하는 username이 없는 경우")
    void findByUsernameFailureTest() {
        //given
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();
        userRepository.save(userMapper.toUserEntity(userCreateDTO));

        //when
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername("whoami");

        //then
        assertFalse(optionalUserEntity.isPresent(), "'whoami'라는 유저가 존재하여 조회했습니다.");
    }

    @Test
    @DisplayName("findByUsername 동작 테스트 - DB에 일치하는 username이 있는 경우")
    void findByUsernameSuccessTest() {
        //given
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();
        userRepository.save(userMapper.toUserEntity(userCreateDTO));

        //when
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername("test");

        //then
        assertTrue(optionalUserEntity.isPresent(), "'test'라는 유저가 존재하지 않습니다.");
    }
}