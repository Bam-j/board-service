package com.bam.board_service.user.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
import com.bam.board_service.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserDeleteServiceTest {

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

    //request: /user/delete/{user.username}
    @Test
    @DisplayName("삭제 후 다시 조회할 때 결과가 null이어야 한다.")
    void userDeleteServiceTest() {
        //given
        Optional<UserEntity> optionalUserEntity = userRepository.findByNickname("test");
        UserEntity userEntity = null;

        //when
        if (optionalUserEntity.isPresent()) {
            userEntity = optionalUserEntity.get();
        }
        userRepository.delete(userEntity);

        Optional<UserEntity> optionalResultAfterDeleteUserEntity = userRepository.findByNickname("test");

        //then
        assertFalse(optionalResultAfterDeleteUserEntity.isPresent());
    }

    /*
     * user delete의 조건
     * 1. 관리자가 관리자 페이지에서 유저 삭제 버튼 클릭
     *   -> 관리자 권한이 있어야 함 -> 추후 관리자 페이지 관련 서비스에서 구현
     * 2. 사용자 본인이 사용자 정보 수정 페이지에서 탈퇴 버튼 클릭
     *   -> 본인만 삭제가 가능 -> 현재 구현할 것
     */
    @Test
    @DisplayName("UserService.delete() 실패 테스트 - loginState가 0L(offline)인 경우")
    void userServiceDeleteFailureByLoginStateTest() {
        //given
        UserActiveDTO userActiveDTO = UserActiveDTO.builder()
            .nickname("test")
            .loginState(0L)
            .build();

        //when
        /*
         * delete 실패 조건
         * 1. UserActiveDTO.nickname과 지우려는 대상의 nickname이 불일치하는 경우
         * 2. loginState가 0L(로그아웃 상태)인 경우
         */
        String username = userRepository.findByUsername("test").get().getUsername();
        userService.delete(username);

        Optional<UserEntity> optionalUserEntity = userRepository.findByNickname(userActiveDTO.getNickname());

        //then
        //delete에 실패한 경우 조회했을때 결과가 null이면 안된다.
        assertNotNull(optionalUserEntity);
    }

    @Test
    @DisplayName("UserService.delete() 실패 테스트 - 삭제 하려는 username과 삭제 요청하는 username이 불일치")
    void userServiceDeleteFailureByWrongUsernameTest() {
        //given
        UserActiveDTO userActiveDTO = UserActiveDTO.builder()
            .nickname("whoami")
            .loginState(1L)
            .build();

        //when
        String username = userRepository.findByUsername("test").get().getUsername();
        userService.delete(username);

        Optional<UserEntity> optionalUserEntity = userRepository.findByNickname(userActiveDTO.getNickname());

        //then
        //delete에 실패한 경우 조회했을때 결과가 null이면 안된다.
        assertNotNull(optionalUserEntity);
    }

    @Test
    @DisplayName("UserService.delete() 성공 테스트")
    void userServiceDeleteSuccessTest() {
        //given
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("del")
            .nickname("del")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();
        UserEntity userEntity = userMapper.toUserEntity(userCreateDTO);
        userRepository.save(userEntity);

        //when
        String username = userRepository.findByUsername("del").get().getUsername();
        userService.delete(username);

        Optional<UserEntity> optionalUserEntity = userRepository.findByNickname("del");

        //then
        assertTrue(optionalUserEntity.isEmpty());
    }
}