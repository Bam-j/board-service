package com.bam.board_service.user.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
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

@SpringBootTest
@Transactional
class UserLoginServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("findByName에 성공했을 때 UserActiveDTO에 nickname, loginState가 제대로 반영되었는지 테스트")
    void changeLoginStateTest() {
        //given
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();
        userRepository.save(userMapper.toUserEntity(userCreateDTO));

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
            .username("test")
            .password("1234")
            .build();

        //when
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername("test");

        UserEntity userEntity = optionalUserEntity.get();

        UserActiveDTO userActiveDTO = UserActiveDTO.builder()
            .nickname(userEntity.getNickname())
            .loginState(1L)
            .build();

        //then
        assertThat(userActiveDTO.getNickname()).isEqualTo(userEntity.getNickname());
        assertThat(userActiveDTO.getLoginState()).isEqualTo(1L);
    }
}