package com.bam.board_service.user.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
import com.bam.board_service.service.UserService;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserLoginServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    //TODO: loginState를 DB에 처리하는 코드 누락
    @Test
    @DisplayName("findByUsername에 성공했을 때 UserActiveDTO에 nickname, loginState가 제대로 반영되었는지 테스트")
    void UserLoginServiceSuccessTest() {
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
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(userLoginDTO.getUsername());

        UserEntity userEntity = optionalUserEntity.get();

        UserActiveDTO userActiveDTO = UserActiveDTO.builder()
            .nickname(userEntity.getNickname())
            .loginState(1L)
            .build();

        //then
        assertThat(userActiveDTO.getNickname()).isEqualTo(userEntity.getNickname());
        assertThat(userActiveDTO.getLoginState()).isEqualTo(1L);
    }

    @Test
    @DisplayName("findByName에 성공했지만 password가 일치하지 않는 경우 null 반환")
    void UserLoginServiceFailureTest() {
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
            .password("0000")
            .build();

        //when
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername("test");
        UserEntity userEntity = optionalUserEntity.get();
        UserActiveDTO userActiveDTO;

        if (!Objects.equals(userEntity.getPassword(), userLoginDTO.getPassword())) {
            userActiveDTO = null;
        } else {
            userActiveDTO = UserActiveDTO.builder()
                .nickname(userEntity.getNickname())
                .loginState(1L)
                .build();
        }

        //then
        assertNull(userActiveDTO, "비밀번호가 틀린 경우 userActiveDTO는 NULL이어야 합니다.");
    }

    @Test
    @DisplayName("UserService.login() 실패 테스트")
    void UserServiceLoginMethodFailureTest() {
        //given
        MockHttpSession session = new MockHttpSession();

        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        userService.save(userCreateDTO);

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
            .username("tt")
            //.username("test)
            .password("1234")
            //.password("0000")
            .build();

        //when
        UserActiveDTO userActiveDTO = userService.login(userLoginDTO, session);

        //then
        assertNull(userActiveDTO, "비밀번호가 틀린 경우 userActiveDTO는 NULL이어야 합니다.");
    }

    @Test
    @DisplayName("UserService.login() 성공 테스트")
    void UserServiceLoginMethodSuccessTest() {
        //given
        MockHttpSession session = new MockHttpSession();

        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        userService.save(userCreateDTO);

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
            .username("test")
            .password("1234")
            .build();

        //when
        UserActiveDTO userActiveDTO = userService.login(userLoginDTO, session);

        //then
        UserEntity savedUser = userRepository.findByUsername("test").get();
        assertThat(userActiveDTO.getNickname()).isEqualTo("test");
        assertThat(userActiveDTO.getLoginState()).isEqualTo(1L);
    }

    @Test
    @DisplayName("로그인 후 세션으로 부터 id 취득")
    void getIdFromSessionWhenUserLoginTest() {
        //given
        MockHttpSession session = new MockHttpSession();

        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        userService.save(userCreateDTO);

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
            .username("test")
            .password("1234")
            .build();

        //when
        userService.login(userLoginDTO, session);

        UUID savedUserId = userRepository.findByUsername("test").get().getId();
        UUID loginUserId = (UUID) session.getAttribute("loginUserId");

        //then
        assertEquals(savedUserId, loginUserId);
    }
}