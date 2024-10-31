package com.bam.board_service.user.service;

import com.bam.board_service.repository.UserRepository;
import com.bam.board_service.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserInfoModifyTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    @DisplayName("")
    void userInfoModifyTest() {
        //given

        //when

        //then
    }
}