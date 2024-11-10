package com.bam.board_service.user.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
import com.bam.board_service.dto.user.UserUpdateDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
import com.bam.board_service.service.UserService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
class UserUpdateServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();
        userRepository.save(userMapper.toUserEntity(userCreateDTO));
    }

    @Test
    @DisplayName("변경하려는 nickname이 이미 DB 내에 존재하는 경우 Optional은 null이면 안된다")
    void userNicknameUpdateFailureTest() {
        //given
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

    @Test
    @DisplayName("UserEntity 탐색 방식을 findByUsername -> findById로 변경")
    void findUserByFindByIdTest() {
        //given
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
            .username("test")
            .password("1234")
            .build();
        MockHttpSession session = new MockHttpSession();
        userService.login(userLoginDTO, session);

        UUID id = (UUID) session.getAttribute("loginUserId");

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .id(id)
            .nickname("test")
            .password("0000")
            .build();

        //when
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        UserEntity originalUserEntity = optionalUserEntity.get();

        UserMapper userMapper = new UserMapper();
        UserEntity updatedUserEntity = userMapper.toUserEntity(originalUserEntity, userUpdateDTO);
        userRepository.save(updatedUserEntity);

        //then
        UserEntity updateResultUserEntity = userRepository.findById(id).get();

        assertThat(updateResultUserEntity.getUsername()).isEqualTo("test");
        assertThat(updateResultUserEntity.getNickname()).isEqualTo("test");
        assertThat(updateResultUserEntity.getPassword()).isEqualTo("0000");
    }

    @Test
    @DisplayName("변경된 방식의 UserService.updateNickname() 실패 테스트 - 동일한 Nickname으로의 변경 시도")
    void changedUserServiceUpdateNicknameFailureTest() {
        //given
        //기존 유저 정보 username: "test", nickname: "test", password: "1234"
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
            .username("test")
            .password("1234")
            .build();
        MockHttpSession session = new MockHttpSession();
        userService.login(userLoginDTO, session);

        UUID id = (UUID) session.getAttribute("loginUserId");

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .id(id)
            .nickname("test")
            .password("1234")
            .build();

        //when
        UserActiveDTO userActiveDTO = userService.updateNickname(id, userUpdateDTO);

        //then
        assertNull(userActiveDTO);
    }

    @Test
    @DisplayName("변경된 방식의 UserService.updateNickname() 성공 테스트")
    void changedUserServiceUpdateNicknameSuccessTest() {
        //given
        //기존 유저 정보 username: "test", nickname: "test", password: "1234"
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
            .username("test")
            .password("1234")
            .build();
        MockHttpSession session = new MockHttpSession();
        userService.login(userLoginDTO, session);

        UUID id = (UUID) session.getAttribute("loginUserId");

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .id(id)
            .nickname("hello")
            .password("0000")
            .build();

        //when
        UserActiveDTO userActiveDTO = userService.updateNickname(id, userUpdateDTO);

        //then
        assertAll(
            () -> assertThat(userActiveDTO).isNotNull(),
            () -> assertThat(userActiveDTO.getNickname()).isEqualTo("hello"),
            () -> assertThat(userActiveDTO.getLoginState()).isEqualTo(1L)
        );
    }

    @Test
    @DisplayName("변경된 방식의 UserService.updatePassword() 실패 테스트 - 동일한 Password로의 변경 시도")
    void changedUserServiceUpdatePasswordFailureTest() {
        //given
        //기존 유저 정보 username: "test", nickname: "test", password: "1234"
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
            .username("test")
            .password("1234")
            .build();
        MockHttpSession session = new MockHttpSession();
        userService.login(userLoginDTO, session);

        UUID id = (UUID) session.getAttribute("loginUserId");

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .id(id)
            .nickname("test")
            .password("1234")
            .build();

        //when
        UserActiveDTO userActiveDTO = userService.updatePassword(id, userUpdateDTO);

        //then
        assertNull(userActiveDTO);
    }

    @Test
    @DisplayName("변경된 방식의 UserService.updatePassword() 성공 테스트")
    void changedUserServiceUpdateNicknamePasswordTest() {
        //given
        //기존 유저 정보 username: "test", nickname: "test", password: "1234"
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
            .username("test")
            .password("1234")
            .build();
        MockHttpSession session = new MockHttpSession();
        userService.login(userLoginDTO, session);

        UUID id = (UUID) session.getAttribute("loginUserId");

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .id(id)
            .nickname("test")
            .password("0000")
            .build();

        //when
        UserActiveDTO userActiveDTO = userService.updatePassword(id, userUpdateDTO);

        //then
        assertAll(
            () -> assertThat(userActiveDTO).isNotNull(),
            () -> assertThat(userActiveDTO.getNickname()).isEqualTo("test"),
            () -> assertThat(userUpdateDTO.getPassword()).isEqualTo("0000"),
            () -> assertThat(userActiveDTO.getLoginState()).isEqualTo(1L)
        );
    }

    /*
     * 이하는 Disabled된 테스트 메소드
     * findByUsername -> findById 변경 이전 테스트 메소드
     */

    @Disabled("Entity 탐색 방식을 findByUsername -> findById로 변경")
    @Test
    @DisplayName("UserService.updateNickname() 실패 테스트 - 이미 존재하는 nickname으로의 변경 시도")
    void userServiceUpdateNicknameMethodFailureTest() {
        //given
        //기존 유저 정보 username: "test", nickname: "test", password: "1234"
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .nickname("test")
            .password("1234")
            .build();

        //when
        //UserActiveDTO userActiveDTO = userService.updateNickname("test", userUpdateDTO);

        //then
        //assertNull(userActiveDTO);
    }

    @Disabled("Entity 탐색 방식을 findByUsername -> findById로 변경")
    @Test
    @DisplayName("UserService.updateNickname() 성공 테스트")
    void userServiceUpdateNicknameMethodSuccessTest() {
        //given
        //기존 유저 정보 username: "test", nickname: "test", password: "1234"
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .nickname("hello")
            .password("1234")
            .build();

        //when
        //UserActiveDTO userActiveDTO = userService.updateNickname("test", userUpdateDTO);

        //then
        /*
        assertAll(
            () -> assertThat(userActiveDTO).isNotNull(),
            () -> assertThat(userActiveDTO.getNickname()).isEqualTo("hello"),
            () -> assertThat(userActiveDTO.getLoginState()).isEqualTo(1L)
        );
         */
    }

    @Disabled("Entity 탐색 방식을 findByUsername -> findById로 변경")
    @Test
    @DisplayName("UserService.updatePassword() 실패 테스트 - 이전에 사용한 비밀번호와 동일한 비밀번호로 변경 시도")
    void userServiceUpdatePasswordMethodFailureTest() {
        //given
        //기존 유저 정보 username: "test", nickname: "test", password: "1234"
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .nickname("test")
            .password("1234")
            .build();

        //when
        //UserActiveDTO userActiveDTO = userService.updatePassword("test", userUpdateDTO);

        //then
        //assertNull(userActiveDTO);
    }

    @Disabled("Entity 탐색 방식을 findByUsername -> findById로 변경")
    @Test
    @DisplayName("UserService.updatePassword() 성공 테스트")
    void userServiceUpdatePasswordMethodSuccessTest() {
        //given
        //기존 유저 정보 username: "test", nickname: "test", password: "1234"
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .nickname("test")
            .password("7777")
            .build();

        //when
        //UserActiveDTO userActiveDTO = userService.updatePassword("test", userUpdateDTO);

        //then
        /*
        assertAll(
            () -> assertThat(userActiveDTO).isNotNull(),
            () -> assertThat(userActiveDTO.getNickname()).isEqualTo("test"),
            () -> assertThat(userUpdateDTO.getPassword()).isEqualTo("7777"),
            () -> assertThat(userActiveDTO.getLoginState()).isEqualTo(1L)
        );

         */
    }
}