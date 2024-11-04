package com.bam.board_service.user.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MockHttpSessionTest {

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
}