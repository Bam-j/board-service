package com.bam.board_service.user.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.repository.UserRepository;
import com.bam.board_service.service.UserService;
import java.util.Optional;
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
class MockHttpSessionTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("MockHttpSession 객체 테스트")
    void mockHttpSessionTest() {
        //given
        MockHttpSession session = new MockHttpSession();
        UserEntity userEntity = UserEntity.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .userType(0L)
            .loginState(1L)
            .build();
        session.setAttribute("user", userEntity);
        session.setAttribute("nickname", userEntity.getNickname());
        session.setAttribute("loginState", userEntity.getLoginState());

        //when
        UserActiveDTO userActiveDTO = UserActiveDTO.builder()
            .nickname(session.getAttribute("nickname").toString())
            .loginState((Long) session.getAttribute("loginState"))
            .build();

        //then
        assertEquals(userEntity.getNickname(), userActiveDTO.getNickname());
        assertEquals(userEntity.getLoginState(), userActiveDTO.getLoginState());
    }

    @Test
    @DisplayName("login 후 로그인한 유저(UserActiveDTO)의 id값을 세션으로부터 가져오기")
    void MockHttpSessionTest() {
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
        Optional<UserEntity> findByUsername = userRepository.findByUsername(
            userLoginDTO.getUsername());
        UserEntity userEntity = findByUsername.get();

        UserActiveDTO userActiveDTO = UserActiveDTO.builder()
            .id(userEntity.getId())
            .nickname(userEntity.getNickname())
            .loginState(1L)
            .build();

        session.setAttribute("userId", userEntity.getId());

        //then
        assertEquals(session.getAttribute("userId"), userActiveDTO.getId());
    }
}